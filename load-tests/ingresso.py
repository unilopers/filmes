from locust import HttpUser, task, between
from faker import Faker
from datetime import datetime, timedelta
import re
import random

fake = Faker('pt_BR')

class IngressoUser(HttpUser):
    wait_time = between(1, 2)

    def on_start(self):
        self.token = None
        self.id_usuario = None
        self.id_sessao = None
        self.id_tipo = None

        self.email = f"load_{fake.uuid4()[:8]}@test.com"
        self.password = "pass123"
        headers_xml = {"Content-Type": "application/xml", "Accept": "application/xml"}

        reg_payload = f"<?xml version='1.0' encoding='UTF-8'?><usuario><nome>{fake.name()}</nome><email>{self.email}</email><password>{self.password}</password></usuario>"
        with self.client.post("/auth/register", data=reg_payload, headers=headers_xml, catch_response=True) as resp:
            if resp.status_code in [200, 201]:
                match = re.search(r'<id[^>]*>(\d+)</id>', resp.text)
                if match:
                    self.id_usuario = match.group(1)
                elif resp.headers.get("Location"):
                    self.id_usuario = resp.headers.get("Location").split("/")[-1]

        login_json = {"email": self.email, "password": self.password}
        with self.client.post("/auth/login", json=login_json, catch_response=True) as log_resp:
            if log_resp.status_code == 200:
                try:
                    self.token = log_resp.json().get("token")
                except Exception:
                    return

        if self.token and self.id_usuario:
            self.prepare_data()

    def prepare_data(self):
        auth_h = {"Authorization": f"Bearer {self.token}", "Content-Type": "application/xml", "Accept": "application/xml"}

        f_xml = f"<?xml version='1.0' encoding='UTF-8'?><filme><titulo>Movie_{fake.uuid4()[:5]}</titulo><duracaoMin>120</duracaoMin><ano>2024</ano></filme>"
        with self.client.post("/filmes", data=f_xml, headers=auth_h, catch_response=True) as fr:
            if fr.status_code == 201:
                loc = fr.headers.get("Location")
                f_id = loc.split("/")[-1] if loc else None
            else: return

        s_xml = f"<?xml version='1.0' encoding='UTF-8'?><sala><nome>Sala_{fake.random_int(1, 999999)}</nome><capacidade>10</capacidade></sala>"
        with self.client.post("/salas", data=s_xml, headers=auth_h, catch_response=True) as sr:
            if sr.status_code == 201:
                loc = sr.headers.get("Location")
                s_id = loc.split("/")[-1] if loc else None
            else: return

        t_xml = f"<?xml version='1.0' encoding='UTF-8'?><tipoIngresso><descricao>Tipo_{fake.uuid4()[:4]}</descricao><fatorPreco>1.0</fatorPreco><categoriaTecnica>2D</categoriaTecnica></tipoIngresso>"
        with self.client.post("/tipos-ingresso", data=t_xml, headers=auth_h, catch_response=True) as tr:
            if tr.status_code == 201:
                loc = tr.headers.get("Location")
                self.id_tipo = loc.split("/")[-1] if loc else None
            else: return

        if f_id and s_id:
            h_xml = f"<?xml version='1.0' encoding='UTF-8'?><homologacao><idFilme>{f_id}</idFilme><idSala>{s_id}</idSala><requisitoTecnico>2D</requisitoTecnico><statusValidacao>Aprovado</statusValidacao></homologacao>"
            self.client.post("/homologacoes", data=h_xml, headers=auth_h, catch_response=True)

            dt = (datetime.now() + timedelta(days=1)).strftime("%Y-%m-%dT%H:%M:%S")
            ss_xml = f"<?xml version='1.0' encoding='UTF-8'?><sessao><idFilme>{f_id}</idFilme><idSala>{s_id}</idSala><dataHora>{dt}</dataHora><precoBase>25.00</precoBase><tipoExibicao>2D</tipoExibicao></sessao>"
            with self.client.post("/sessoes", data=ss_xml, headers=auth_h, catch_response=True) as sssr:
                if sssr.status_code == 201:
                    loc = sssr.headers.get("Location")
                    self.id_sessao = loc.split("/")[-1] if loc else None

    @task
    def ciclo_compra_ingresso(self):
        if not (self.token and self.id_usuario and self.id_sessao and self.id_tipo):
            return

        headers = {"Authorization": f"Bearer {self.token}", "Content-Type": "application/xml", "Accept": "application/xml"}
        assento = random.randint(1, 10)
        payload = f"<?xml version='1.0' encoding='UTF-8'?><compraIngresso><idUsuario>{self.id_usuario}</idUsuario><idSessao>{self.id_sessao}</idSessao><idTipoIngresso>{self.id_tipo}</idTipoIngresso><numeroAssento>{assento}</numeroAssento></compraIngresso>"

        with self.client.post("/ingressos", data=payload, headers=headers, catch_response=True, name="POST /ingressos") as response:
            if response.status_code == 201:
                location = response.headers.get("Location")
                if location:
                    with self.client.get(location, headers=headers, catch_response=True, name="GET /ingressos/[id]") as get_resp:
                        if get_resp.status_code in [200, 403]:
                            get_resp.success()
                        else:
                            get_resp.failure(f"Erro GET: {get_resp.status_code}")
                response.success()
            elif response.status_code == 400:
                response.success()
            else:
                response.failure(f"Erro POST: {response.status_code}")