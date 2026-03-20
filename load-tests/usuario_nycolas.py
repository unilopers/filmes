from locust import HttpUser, task, between
from faker import Faker

fake = Faker()


class UsuarioUser(HttpUser):
    host = "http://localhost:8080"
    wait_time = between(1, 3)

    def on_start(self):
        self.email = fake.email()
        self.password = "123456"
        self.nome = fake.name()

        register_xml = f"""
        <usuario>
            <nome>{self.nome}</nome>
            <email>{self.email}</email>
            <password>{self.password}</password>
        </usuario>
        """

        self.client.post(
            "/auth/register",
            data=register_xml,
            headers={"Content-Type": "application/xml"}
        )

        login_xml = f"""
        <loginRequest>
            <email>{self.email}</email>
            <password>{self.password}</password>
        </loginRequest>
        """

        response = self.client.post(
            "/auth/login",
            data=login_xml,
            headers={"Content-Type": "application/xml"}
        )

        self.token = response.json().get("token")

        self.headers = {
            "Content-Type": "application/xml",
            "Authorization": f"Bearer {self.token}"
        }

    @task
    def criar_e_buscar_usuario(self):
        nome = fake.name()
        email = fake.email()
        password = "123456"

        create_xml = f"""
        <usuario>
            <nome>{nome}</nome>
            <email>{email}</email>
            <password>{password}</password>
        </usuario>
        """

        response = self.client.post(
            "/usuarios",
            data=create_xml,
            headers=self.headers
        )

        if response.status_code == 201:
            location = response.headers.get("Location")

            if location:
                self.client.get(location, headers=self.headers)