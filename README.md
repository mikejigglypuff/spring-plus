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

