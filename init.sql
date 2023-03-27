CREATE USER vigenereUser WITH PASSWORD 'vigenerepassword';
ALTER USER vigenereUser SUPERUSER;
CREATE DATABASE vigenere;
GRANT ALL PRIVILEGES ON DATABASE vigenere TO vigenereuser;