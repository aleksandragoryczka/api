# GitHub Repositories list API


## Table of contents

- [Introduction](#introduction)
- [Requirements](#requirements)
- [Launching](#launching)
- [API](#api)
- [Usage example](#usage-example)


## Introduction
This project is API based on username provided by user nad as the response returns:
- repository name (which are not forks)
- owner login
- branch names & their last commits sha


## Requirements
The list of tools required to build and run the project:
- JDK 17
- Apache Maven 4.0.0


## Launching
Execute the `main` method in the `com/github/api/ApiApplication.java` class from your IDE.


## API
External API used:
- https://developer.github.com/v3


## Usage example

User launches application.

User creates request to the URL `http://localhost:8080/api/{username}` with header `Accept: application/json`.

As the response user receive list of non-forked repositories included their names owner login and branches' names with
their commit sha.

#### Example:
Request URL: `http://localhost:8080/api/aleksandragoryczka`
Response body:
```
[
    {
        "name": "adventOfCode",
        "owner": {
            "login": "aleksandragoryczka"
        },
        "branches": [
            {
                "name": "main",
                "commit": {
                    "sha": "175286db31a706e43f710ade40def30b0eceab28"
                }
            }
        ]
    },
    {
        "name": "recipeFinder",
        "owner": {
            "login": "aleksandragoryczka"
        },
        "branches": [
            {
                "name": "dev",
                "commit": {
                    "sha": "0b85896b5530d323245a1507239629a5ac805590"
                }
            },
            {
                "name": "main",
                "commit": {
                    "sha": "0b85896b5530d323245a1507239629a5ac805590"
                }
            }
        ]
    }
]
```

If user with given username does not exist in GitHub, response body will look like:

```
{
    "status": 404,
    "message": "User not found: aleksandragoryczkatgtgdfeeddededede"
}
```

If header include "Accept: application/xml", response body will look like:
```
{
    "status": 406,
    "message": "Not Acceptable Header"
}
```
