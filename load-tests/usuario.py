from locust import HttpUser, task, between
from faker import Faker

fake = Faker('pt_BR')

class UsuarioUser(HttpUser):
    wait_time = between(1, 2)

    def on_start(self):
        self.token = None
        self.email = f"load_{fake.uuid4()[:8]}@test.com"
        self.password = "pass123"
        headers_xml = {"Content-Type": "application/xml", "Accept": "application/xml"}

        reg_payload = f"<?xml version='1.0' encoding='UTF-8'?><usuario><nome>{fake.name()}</nome><email>{self.email}</email><password>{self.password}</password></usuario>"

        with self.client.post("/auth/register", data=reg_payload, headers=headers_xml, catch_response=True) as resp:
            if resp.status_code not in [200, 201]:
                return

        login_json = {"email": self.email, "password": self.password}
        with self.client.post("/auth/login", json=login_json, catch_response=True) as log_resp:
            if log_resp.status_code == 200:
                try:
                    self.token = log_resp.json().get("token")
                except Exception:
                    return

    @task
    def ciclo_completo_usuario(self):
        if not self.token:
            return

        headers = {"Authorization": f"Bearer {self.token}", "Content-Type": "application/xml", "Accept": "application/xml"}
        novo_email = f"user_{fake.uuid4()[:8]}@test.com"

        xml = f"<?xml version='1.0' encoding='UTF-8'?><usuario><nome>{fake.name()}</nome><email>{novo_email}</email><password>123456</password></usuario>"

        with self.client.post("/usuarios", data=xml, headers=headers, catch_response=True, name="POST /usuarios") as response:
            if response.status_code == 201:
                location = response.headers.get("Location")
                if location:
                    self.client.get(location, headers=headers, name="GET /usuarios/[id]")
            else:
                response.failure(f"Erro POST: {response.status_code}")
