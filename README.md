# Logavera Core ![GitHub](https://img.shields.io/github/license/logavera/logavera-core) ![GitHub issues](https://img.shields.io/github/issues/logavera/logavera-core)

**Tamper-proof agreement and consent infrastructure**

Logavera Core is an open-source library for building **verifiable agreement and consent systems**.  
It provides **immutable document versioning**, **cryptographic integrity checks**, and primitives for recording consent.

Designed for applications that require **auditable proof of user agreement**, Logavera ensures every consent is **cryptographically tied to a specific document version**.

---

## 📑 Table of Contents

- [Features](#-features)  
- [Core Concepts](#-core-concepts)  
  - [Documents](#documents)  
  - [Versions](#versions)  
  - [Consent](#consent)  
  - [Access Log](#access-log)  
  - [Engine](#engine)  
- [Sandbox](#-sandbox)  
- [Security Model](#-security-model)  
- [Contributing](#-contributing)  
- [License](#-license)  

---

## 🚀 Features

- Immutable document versions  
- Cryptographic document hashing  
- Consent bound to document version  
- Verifiable proof generation  
- Audit-ready metadata  

**Use cases:**  

- Acceptance of Terms of Service  
- Privacy policy consent  
- Parental consent  
- Delegated authorization  
- Legal agreements  
- Regulatory compliance flows  

---

## 🧩 Core Concepts

### Documents

A **Document** represents a logical agreement.

Examples:  

- Privacy Policy  
- Terms of Service  
- Data Processing Agreement  

Documents can contain multiple child documents and multiple versions.

### Versions

A **Version** is an immutable snapshot of a document.

Each version contains:  

- Content hash (SHA-256)  
- Hash of the previous version  

All elements are signed by a secret key, preventing tampering or manipulation.

### Consent

A **Consent** is an immutable record of a user accepting a specific document version.

Each consent contains:  

- Signed document version hash (SHA-256)  
- Hash of the previous consent  
- Metadata (IP address, user agent, UI context)  

Signed by a secret key, it proves **exactly when and what was accepted or rejected**.

### Access Log

An **Access Log** records a user fetching a specific document version.

Each access log contains:  

- Signed document version hash (SHA-256)  
- Hash of the previous access log  
- Metadata (IP address, user agent, UI context)  

This proves the user **viewed the document before giving consent**, ensuring transparency.

### Engine

The **Engine** evaluates resources and outputs a list of **fully consented documents**, meaning:  

- All document versions have recorded consent  
- All child documents have recorded consent  

---

## 🧪 Sandbox

Try it live: [www.logavera.com/sandbox](https://www.logavera.com/sandbox)

### Components

- `logavera-core` – Core domain model & consent evaluation engine  
- `logavera-integrity` – Hashing mechanisms & domain model extensions  
- `logavera-spring-boot-starter` – Spring Boot integration  

Managed service available at: [https://logavera.com](https://logavera.com)

---

## 🔒 Security Model

### Core Principles

- Immutable document versions and consents  
- Content hashing  
- Signed proofs  
- Transparent verification  

---

## 🤝 Contributing

Contributions are welcome!

**Areas for contribution:**  

- Integrations  
- Additional proof formats  
- Storage adapters  
- Developer tooling  

---

## 📄 License
[LICENSE](LICENSE)
