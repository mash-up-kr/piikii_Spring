from locust import HttpUser, task, between
import uuid

class APIUser(HttpUser):
    wait_time = between(1, 3)  # 각 태스크 사이의 대기 시간을 1~3초로 설정

    # @task
    # def test_entity_random_endpoint(self):
    #     # UUID 생성
    #     uid = str(uuid.uuid4())
        
    #     # API 엔드포인트에 POST 요청 보내기
    #     self.client.post(f"/api/v1/test/entity/{uid}")
    @task
    def test_entity_fix_endpoint(self):
        # 고정 UUID
        uid = "7afcc705-8ce8-4bde-9904-9cca8e57a1a1"
        
        # API 엔드포인트에 POST 요청 보내기
        self.client.post(f"/api/v1/test/entity/{uid}")
