# Logavera Core

**Tamper-proof agreement and consent infrastructure.**

Logavera Core is an open-source library for building **verifiable agreement and consent systems**.  
It provides immutable document versioning, cryptographic integrity checks, and consent recording primitives.

The library is designed to be embedded into applications that require **auditable proof of user agreement**.

Typical use cases include:

- Terms of Service acceptance
- Privacy policy consent
- parental consent
- delegated authorization
- legal agreements
- regulatory compliance flows

Logavera ensures that every consent can be **cryptographically tied to a specific document version**.

---

# Why?

Logavera provides strong guarantees:

- immutable document versions
- cryptographic document hashing
- consent bound to document version
- verifiable proof generation
- audit-ready metadata

This allows systems to demonstrate **what exactly was accepted and when**.

---

# Core Concepts

## Documents

A **Document** represents a logical agreement.

Examples:

- Privacy Policy
- Terms of Service
- Data Processing Agreement

Document may contain multiple child Documents, and multiple Document Versions.

## Versions

A **Version** is an immutable snapshot of a document.

Each version includes a content hash (SHA-256), a hash of the previous Document version—all together signed by a secret
key.
This prevents injection or modification of content and other properties, which could otherwise be used to manipulate the
Engine output.

## Consent

A **Consent** is an immutable representation of a user accepting a specific document version.

Each Consent includes a signed Document Version hash (SHA-256), a hash of the previous Consent, metadata—all together
signed by a secret key.
This prevents injection or modification of content and other properties, which could otherwise be used to manipulate the
Engine output.

Example metadata:

- IP address
- user agent
- UI presentation context

Consent record is a proof that a certain Document Version was consented/rejected at a certain point in time.

## Access Log

An **Access Log** is an immutable representation of a user fetching a specific document version.

Each Access Log includes a signed Document Version hash (SHA-256), a hash of the previous Access Log, metadata—all
together signed by a secret key.
This prevents injection or modification of content and other properties, which could otherwise be used to manipulate the
Engine output.

Example metadata:

- IP address
- user agent
- UI presentation context

Access Log is a proof that a certain Document Version was fetched before giving a consent-to prove that the
consentee was presented with the correct version of the document at the moment of giving a consent.

## Engine

Engine is the main entry point. It performs evaluation of the provided resources and outputs a list of fully consented
documents.
Fully consented document means:
- all its document versions have consent
- all its children documents have consent

# Sandbox
You can try it here: www.logavera.com/sandbox

## Components:

```logavera-core``` core domain model and consent evaluation engine

```logavera-integrity``` hashing mechanisms and domain model extension

```logavera-spring-boot-starter``` Spring Boot integration

A managed Logavera service is available at:

https://logavera.com

# Security Model

### Core principles

- immutable document versions and consents

- content hashing

- signed proofs

- transparent verification

# Contributing

Contributions are welcome.

### Typical contribution areas

- integrations

- additional proof formats

- storage adapters

- developer tooling

# License
[LICENSE](LICENSE)