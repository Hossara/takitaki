# Gateway
TakiTaki microservice gateway and auth service

- [x] Load-Balancing and routing
- [X] Register and validate user
- [ ] Authenticate user and login

### Environment Variables
Please create `.env` file in project classpath: `/src/main/resources` or define 
your variables in runtime environment!.

| key           | type    | example               |
|---------------|---------|-----------------------|
| MAIL_SERVICE  | string  | http://localhost:8000 |
| CRASH_SERVICE | string  | http://localhost:8001 |
| PROJECT_URL   | boolean | http://example.com    |
| DEVELOPMENT   | boolean | true                  |
