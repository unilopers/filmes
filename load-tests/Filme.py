import uuid
from locust import HttpUser, task, between
from faker import Faker

fake = Faker('pt_BR')

class FilmeUser(HttpUser):
    # Tempo de espera entre as ações (1 a 2 segundos)
    wait_time = between(1, 2)

    def on_start(self):
        self.token = None
        self.filme_id = None
        
        # 1. GERAR DADOS PARA REGISTRO (Corrigido para usar uuid nativo)
        email_fake = f"load_{uuid.uuid4().hex[:8]}@test.com"
        senha_fake = "pass123"
        
        # Dados de fallback (caso o registro falhe)
        self.default_user = "joao.silva@example.com"
        self.default_pass = "senha123"

        # 2. TENTATIVA DE REGISTRO
        reg_payload = {
            "nome": fake.name(),
            "email": email_fake,
            "password": senha_fake
        }
        
        print(f"Tentando registro para: {email_fake}")
        with self.client.post("/auth/register", json=reg_payload, catch_response=True) as resp:
            if resp.status_code in [200, 201]:
                print(f"✅ REGISTRO OK!")
                self.email_final = email_fake
                self.pass_final = senha_fake
            else:
                print(f"⚠️ Registro negado ({resp.status_code}). Tentando login com usuário padrão...")
                self.email_final = self.default_user
                self.pass_final = self.default_pass
                resp.success() 

        # 3. LOGIN
        login_data = {"email": self.email_final, "password": self.pass_final}
        with self.client.post("/auth/login", json=login_data, catch_response=True) as log_resp:
            if log_resp.status_code == 200:
                self.token = log_resp.json().get("token")
                print("🔑 TOKEN OBTIDO!")
                self.prepare_data()
            else:
                print(f"❌ FALHA NO LOGIN: {log_resp.status_code}")
                log_resp.failure(f"Login falhou: {log_resp.status_code}")

    def prepare_data(self):
        """ Cria um filme inicial """
        if not self.token: return
        headers = {"Authorization": f"Bearer {self.token}"}
        
        payload = {
            "titulo": f"Filme Inicial {fake.word()}",
            "duracaoMin": 120,
            "ano": 2024,
            "generosIds": [1] 
        }
        
        with self.client.post("/api/filmes", json=payload, headers=headers) as resp:
            if resp.status_code in [200, 201]:
                self.filme_id = resp.json().get("id")
                print(f"🎬 Filme de teste criado! ID: {self.filme_id}")

    @task(2)
    def buscar_filme(self):
        """ Testa o GET (Leitura) """
        if self.token and self.filme_id:
            headers = {"Authorization": f"Bearer {self.token}"}
            self.client.get(f"/api/filmes/{self.filme_id}", headers=headers, name="GET /api/filmes/[id]")

    @task(1)
    def criar_filme(self):
        """ Testa o POST (Criação) """
        if self.token:
            headers = {"Authorization": f"Bearer {self.token}"}
            payload = {
                "titulo": f"Carga_{uuid.uuid4().hex[:6]}",
                "duracaoMin": 100,
                "ano": 2024,
                "generosIds": [1]
            }
            self.client.post("/api/filmes", json=payload, headers=headers, name="POST /api/filmes")
