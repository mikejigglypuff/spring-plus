# SPRING PLUS

### Health Check API
| Endpoint | Response                                       | Token Required |
|----------|------------------------------------------------|----------------|
| /health  | [HealthCheckResponse](####healthCheckResponse) | false          |

### API Responses
<details>
<summary>응답 데이터 형식</summary>

#### healthCheckResponse
```json
{
  "serverStatus": "OK",
  "freeDiskGb": "여유 디스크 GB",
  "totalDiskGb": "전체 디스크 GB",
  "freeMemoryGb": "여유 물리 메모리 GB",
  "totalMemoryGb": "전체 물리 메모리 GB",
  "freeJvmMemoryMb": "JVM 내 여유 메모리 MB",
  "totalJvmMemoryMb": "JVM이 할당받은 총 메모리 MB",
  "dbConnected": "DB 연결 여부"
}
```

</details>

### 동등 조건 조회 최적화

0. 최적화 전
![최적화 전](/doc/img/최적화%20전%20닉네임%20기반%20유저%20조회%20속도.png)


1. B- Tree Index 도입 후
![B-Tree](/doc/img/b-tree%20적용.png)


2. B- Tree Index + Adaptive Hash Index 도입 후
![B-Tree&AHI](/doc/img/b-tree%20&%20ahi%20적용.png)


3. FullText Index 도입 후
![FullText](/doc/img/fulltext%20적용.png)


4. FullText Index + Adaptive Hash Index 도입 후
![FullText&AHI](/doc/img/fulltext%20index%20&%20ahi%20적용.png)