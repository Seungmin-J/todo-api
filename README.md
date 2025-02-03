# Todo App
할일을 작성하고 관리하는 앱<br>
## Introduction
할일을 작성하고 관리하는 기본 CRUD 기능을 포함한 API입니다
<br>
## Tech Stack
  - **Language**:  
  ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)

- **Database**:  
  ![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)

- **Tools**:  
  ![Git](https://img.shields.io/badge/Git-F05033?style=for-the-badge&logo=git&logoColor=white)  
  ![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)

## Installation
리포지토리 클론:
```bash
  git clone https://github.com/Seungmin-J/todo-api.git
```

# **Todo API 명세서**

## **1. 일정 생성**
| **Method** | **URL**  | **Request Body**                                                                                                                                                                                                                  | **Response**                                                                                                                                           | **Status**                           |
|------------|----------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------|
| `POST`     | `/todos` | ```json { ```<br/> ```"text": "String 할 일",```<br/> ``` "name": "String 작성자 이름", ```<br/> ``` "password": "String 비밀번호", ```<br/> ```"createdAt": "LocalDateTime 일정 생성 시간", ```<br/> ```"editedAt": "LocalDateTime 일정 수정 시간" }``` | TodoResponseDto | CREATED : 201<br/> BAD_REQUEST : 400 |

---

## **2. 전체 일정 조회 / 수정일, 작성자명 기준 조회**
| **Method** | **URL** | **Request Param**             | **Request Body** | **Response**           | **Status** |
|------------|---------|-------------------------------|------------------|------------------------|------------|
| `GET`      | `/todos` | `name`: 작성자명, `editedAt`: 수정일 |  -               | List\<TodoResponseDto> | OK : 200   |

---

## **3. 선택 일정 조회**
| **Method** | **URL** | **PathVariable** | **Request Body** | **Response**    | **Status**                   |
|------------|---------|--------------------|------------------|-----------------|------------------------------|
| `GET`      | `/todos/{id}` | `id`: 조회할 일정의 ID | -                | TodoResponseDto | OK : 200<br/>NOT_FOUND : 404 |

---

## **4. 선택 일정, 이름 수정**
| **Method** | **URL** | **PathVariable** | **Request Body** | **Response**    | **Status** |
|------------|---------|--------------------|------------------|-----------------|------------|
| `PUT`      | `/todos/{id}` | `id`: 수정할 일정의 ID | ```json { "name": "String 수정된 작성자 이름" }``` | TodoResponseDto | -          |

---

## **5. 선택 일정 삭제**
| **Method** | **URL** | **PathVariable** | **Request Body**              | **Response** | **Status** |
|------------|---------|-------------------|-------------------------------|--------------|------------|
| `DELETE`   | `/todos/{id}` | `id`: 삭제할 일정의 ID | ```"your_password"(text형식)``` | -            | -          |


# **ERD**
![img.png](src/main/resources/static/img.png)

## Contact
- **Email**: [seungmin103@gmail.com](mailto:seungmin103@gmail.com)  
- **GitHub**: [Seungmin-J](https://github.com/Seungmin-J)  
