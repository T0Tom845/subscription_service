CREATE SCHEMA IF NOT EXISTS subscription_scheme;

CREATE TABLE IF NOT EXISTS subscription_scheme.users
(
    id         uuid PRIMARY KEY,
    username   VARCHAR(255) NOT NULL UNIQUE,
    email      VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS subscription_scheme.subscriptions
(
    id         uuid PRIMARY KEY,
    name       VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS subscription_scheme.user_subscriptions
(
    user_id         uuid NOT NULL,
    subscription_id uuid NOT NULL,
    PRIMARY KEY (user_id, subscription_id),
    FOREIGN KEY (user_id) REFERENCES subscription_scheme.users (id) ON DELETE CASCADE,
    FOREIGN KEY (subscription_id) REFERENCES subscription_scheme.subscriptions (id) ON DELETE CASCADE
);