# PDF Unlocker Web App

A Spring Boot-based RESTful web application that allows users to unlock multiple password-protected PDF files in a single request.

## 🚀 Features

- Unlock multiple PDF files at once
- Single common password for all PDFs
- Multipart file upload support
- Built with Java 21 and Spring Boot 3.2.5

---

## 🛠 Tech Stack

- **Java** 21
- **Spring Boot** 3.2.5
- **Spring Web (REST API)**
- **Apache PDFBox** (or similar PDF processing library)

---

## 📦 API Usage

### 🔓 Unlock PDFs

**Endpoint**:  
`POST /unlock-pdf`

**Content-Type**:  
`multipart/form-data`

**Request Parameters:**

| Field     | Type        | Description                               |
|-----------|-------------|-------------------------------------------|
| `files`   | Multipart[] | One or more PDF files to be unlocked      |
| `password`| String      | Common password for all uploaded PDFs     |

**Sample cURL Request:**

```bash
curl --location 'http://localhost:8090/unlock-pdf' \
--form 'files=@"/C:/Users/patra/Downloads/myallpayslips/Compensation revision_2024-25_Mr. Girish Chandra Patra  _41199_ENC.pdf"' \
--form 'password="NPCI@2024"'

