import uuid
from locust import HttpUser, task, between
from faker import Faker

fake = Faker('pt_BR')

class SalaUser(HttpUser):
    wait_time = between(1, 2)

    def on_start(self):
        self.token = None
        self.sala_id = None
        
        test_email = f"load_sala_{uuid.uuid4().hex[:6]}@test.com"
        test_password = "senha_de_teste"

        # Registro e Login
        self.client.post("/auth/register", json={"nome": "Tester Sala", "email": test_email, "password": test_password})
        response = self.client.post("/auth/login", json={"email": test_email, "password": test_password})
        
        if response.status_code == 200:
            self.token = response.json().get("token")
        else:
            response.failure(f"Login falhou: {response.status_code}")

    @task
    def fluxo_sala(self):
        if not self.token: return
        headers = {"Authorization": f"Bearer {self.token}"}

        # POST - Criar Sala
        payload = {"nome": f"Sala_{uuid.uuid4().hex[:4]}", "capacidade": fake.random_int(min=50, max=200)}
        with self.client.post("/salas", json=payload, headers=headers, catch_response=True) as post_res:
            if post_res.status_code in [200, 201]:
                sala_id = post_res.json().get("id")
                if sala_id:
                    # GET - Ler Sala criada
                    self.client.get(f"/salas/{sala_id}", headers=headers, name="GET /salas/[id]")
                post_res.success()
            else:
                post_res.failure(f"Erro POST /salas: {post_res.status_code}")
