import uuid
from locust import HttpUser, task, between
from faker import Faker

fake = Faker('pt_BR')

class GeneroUser(HttpUser):
    wait_time = between(1, 2)

    def on_start(self):
        self.token = None
        
        test_email = f"load_gen_{uuid.uuid4().hex[:6]}@test.com"
        test_password = "senha_de_teste"

        # Registro e Login
        self.client.post("/auth/register", json={"nome": "Tester Genero", "email": test_email, "password": test_password})
        response = self.client.post("/auth/login", json={"email": test_email, "password": test_password})
        
        if response.status_code == 200:
            self.token = response.json().get("token")
        else:
            response.failure(f"Login falhou: {response.status_code}")

    @task
    def fluxo_genero(self):
        if not self.token: return
        headers = {"Authorization": f"Bearer {self.token}"}

        # POST - Criar Genero
        payload = {"nome": f"Gênero_{fake.word()}_{uuid.uuid4().hex[:4]}"}
        with self.client.post("/generos", json=payload, headers=headers, catch_response=True) as post_res:
            if post_res.status_code in [200, 201]:
                genero_id = post_res.json().get("id")
                if genero_id:
                    # GET - Ler Genero criado
                    self.client.get(f"/generos/{genero_id}", headers=headers, name="GET /generos/[id]")
                post_res.success()
            else:
                post_res.failure(f"Erro POST /generos: {post_res.status_code}")
