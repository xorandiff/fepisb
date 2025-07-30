# Fepisb — First Ever Project in Spring Boot

**Recruitment task for _Atipera_**

A minimal Kotlin + Spring Boot 3.5 REST API to list all **non-fork** GitHub repositories for a user, including each branch’s name and last commit SHA.

---

## Running the Application

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/fepisb.git
   cd fepisb
   ```
2. (Optional) Export your GitHub token:
   ```bash
   export GITHUB_TOKEN=your_personal_access_token
   ```
3. Build and run:
   ```bash
   ./gradlew bootRun
   ```
4. The app will start on **`http://localhost:8080`**

---

## API Endpoints

### List User Repositories

```
GET /github/{username}/repos
```

- **Path Parameter**
    - `username` (string): GitHub username to query

- **Success (200 OK)**
    - **Response Body**: JSON array of repository objects
    - **Schema**:
      ```jsonc
      [
        {
          "name": "repository-name",
          "ownerLogin": "username",
          "branches": [
            {
              "name": "branch-name",
              "lastCommitSha": "commit-sha"
            }
          ]
        }
      ]
      ```

---

## Examples

1. **Successful Request**
   ```bash
   curl http://localhost:8080/github/octocat/repos
   ```
   **Response (200)**
   ```json
   [
     {
       "name": "Spoon-Knife",
       "ownerLogin": "octocat",
       "branches": [
         { "name": "main", "lastCommitSha": "a1b2c3d4e5" },
         { "name": "develop", "lastCommitSha": "f6g7h8i9j0" }
       ]
     }
   ]
   ```

2. **User Not Found (404)**
   ```bash
   curl http://localhost:8080/github/nonexistentuser/repos
   ```
   **Response (404)**
   ```json
   {
     "status": 404,
     "message": "GitHub user 'nonexistentuser' not found"
   }
   ```

3. **Rate Limit Exceeded (429)**
   ```bash
   curl http://localhost:8080/github/octocat/repos
   ```
   **Response (429)**
   ```json
   {
     "status": 429,
     "message": "GitHub API rate limit exceeded."
   }
   ```

---

## Testing

Run the integration test which verifies the happy path against the real GitHub API (watch for rate limits):

```bash
./gradlew test
```

