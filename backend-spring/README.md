In this branch has only one Authserver, only one SecurityConfig, serving two clientids with two separate UserDetailServices.

Problems:
* both UserdetailServices are tested
* only one single token endpoint

Both configs working on the single token endpoint, means i can get a token with both clientids:

config 1

```
curl -X POST http://localhost:8080/oauth1/token -H 'Authorization: Basic dGVzdGp3dGNsaWVudGlkOlhZN2ttem9OemwxMDA=' --data "username=user&password=password&grant_type=password"
```

config 2

```
curl -X POST http://localhost:8080/oauth1/token -H 'Authorization: Basic dGVzdGp3dGNsaWVudGlkMjpYWTdrbXpvTnpsMTAwMg==' --data "username=user2&password=password2&grant_type=password"
```
