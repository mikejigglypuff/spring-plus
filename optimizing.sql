-- DB 수준에서 최적화를 수행해 JPA 코드를 변경하지 않고 조회 성능 개선

-- 일반 인덱스 적용
CREATE INDEX idx_name ON users(nickname);
ANALYZE TABLE users;
#DROP INDEX idx_name ON users;

-- FULLTEXT 인덱스 적용
CREATE FULLTEXT INDEX full_text_name ON users(nickname);
ANALYZE TABLE users;
#DROP INDEX full_text_name ON users;

-- Adaptive Hash 인덱스 적용
set global innodb_adaptive_hash_index = 1;
SHOW VARIABLES LIKE 'innodb_adaptive_hash_index';
#set global innodb_adaptive_hash_index = 0;