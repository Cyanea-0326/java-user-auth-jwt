-- `users` テーブルの作成
CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(36) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- `user_email` テーブルの作成
CREATE TABLE user_email (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT user_email_fk FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE
);

-- `user_login_hashed_password` テーブルの作成
CREATE TABLE user_login_hashed_password (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID NOT NULL UNIQUE,
    hashed_password TEXT NOT NULL,
    CONSTRAINT user_login_hashed_password_fk FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE
);

-- `user_detail` テーブルの作成
CREATE TABLE user_detail (
    user_id UUID PRIMARY KEY,
    image_url TEXT,
    bio VARCHAR(160),
    CONSTRAINT user_detail_fk FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE
);

-- `user_role` テーブルの作成 (例)
CREATE TABLE user_role (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID NOT NULL,
    role VARCHAR(50) NOT NULL,
    CONSTRAINT user_role_fk FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE
);

-- `user_email` テーブルにインデックスを作成 (メールアドレスに対して UNIQUE 制約)
CREATE UNIQUE INDEX user_email_email_uindex
    ON public.user_email (email);

-- `user_login_hashed_password` テーブルにインデックスを作成 (user_id に対して UNIQUE 制約)
CREATE UNIQUE INDEX user_login_hashed_password_user_id_uindex
    ON public.user_login_hashed_password (user_id);
