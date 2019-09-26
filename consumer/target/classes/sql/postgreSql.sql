/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : PostgreSQL
 Source Server Version : 100009
 Source Host           : localhost:5432
 Source Catalog        : atomikos
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 100009
 File Encoding         : 65001

 Date: 06/09/2019 11:33:16
*/


-- ----------------------------
-- Sequence structure for c_accout_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."c_accout_id_seq";
CREATE SEQUENCE "public"."c_accout_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for commodity_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."commodity_id_seq";
CREATE SEQUENCE "public"."commodity_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 99999999
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for order_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."order_id_seq";
CREATE SEQUENCE "public"."order_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for user_informations_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."user_informations_id_seq";
CREATE SEQUENCE "public"."user_informations_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Table structure for c_account
-- ----------------------------
DROP TABLE IF EXISTS "public"."c_account";
CREATE TABLE "public"."c_account" (
  "id" int4 NOT NULL DEFAULT nextval('c_accout_id_seq'::regclass),
  "accout_code" varchar(255) COLLATE "pg_catalog"."default",
  "accout_name" varchar(255) COLLATE "pg_catalog"."default",
  "account_moey" int8,
  "order_code" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."c_account"."accout_code" IS '用户账号';
COMMENT ON COLUMN "public"."c_account"."accout_name" IS '用户名';
COMMENT ON COLUMN "public"."c_account"."account_moey" IS '账号金额';
COMMENT ON COLUMN "public"."c_account"."order_code" IS '运单号';
COMMENT ON TABLE "public"."c_account" IS '商户账号';

-- ----------------------------
-- Table structure for commodity
-- ----------------------------
DROP TABLE IF EXISTS "public"."commodity";
CREATE TABLE "public"."commodity" (
  "id" int4 NOT NULL DEFAULT nextval('commodity_id_seq'::regclass),
  "commodity_name" varchar(255) COLLATE "pg_catalog"."default",
  "price" numeric(10,2),
  "commodity_code" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."commodity"."commodity_name" IS '商品名';
COMMENT ON COLUMN "public"."commodity"."price" IS '价格';
COMMENT ON COLUMN "public"."commodity"."commodity_code" IS '商品代码';

-- ----------------------------
-- Records of commodity
-- ----------------------------
INSERT INTO "public"."commodity" VALUES (9, '手机', 1000.00, '1a836839accd4773ab69a6add3e3283c');
INSERT INTO "public"."commodity" VALUES (11, '手机', 1000.00, 'f3bc6fc2800a4de2a7402d7cc2a03aa8');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS "public"."order";
CREATE TABLE "public"."order" (
  "id" int4 NOT NULL DEFAULT nextval('order_id_seq'::regclass),
  "order_code" varchar(255) COLLATE "pg_catalog"."default",
  "order_name" varchar(255) COLLATE "pg_catalog"."default",
  "order_count" int4 DEFAULT nextval('order_id_seq'::regclass),
  "commodityId" int4
)
;
COMMENT ON COLUMN "public"."order"."order_code" IS '订单码';
COMMENT ON COLUMN "public"."order"."order_name" IS '订单名称';
COMMENT ON COLUMN "public"."order"."order_count" IS '订单数量';
COMMENT ON COLUMN "public"."order"."commodityId" IS '商品Id';

-- ----------------------------
-- Table structure for user_informations
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_informations";
CREATE TABLE "public"."user_informations" (
  "id" int4 NOT NULL DEFAULT nextval('user_informations_id_seq'::regclass),
  "userid" int8,
  "email" varchar(32) COLLATE "pg_catalog"."default",
  "address" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."user_informations"."id" IS '主键id';
COMMENT ON COLUMN "public"."user_informations"."userid" IS '用户id';
COMMENT ON COLUMN "public"."user_informations"."email" IS '邮件';
COMMENT ON COLUMN "public"."user_informations"."address" IS '地址';

-- ----------------------------
-- Records of user_informations
-- ----------------------------
INSERT INTO "public"."user_informations" VALUES (27, 666, 'dsb', NULL);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."c_accout_id_seq"', 2, false);
SELECT setval('"public"."commodity_id_seq"', 12, true);
SELECT setval('"public"."order_id_seq"', 2, false);
SELECT setval('"public"."user_informations_id_seq"', 28, true);

-- ----------------------------
-- Primary Key structure for table c_account
-- ----------------------------
ALTER TABLE "public"."c_account" ADD CONSTRAINT "p_account_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table commodity
-- ----------------------------
ALTER TABLE "public"."commodity" ADD CONSTRAINT "commodity_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table order
-- ----------------------------
ALTER TABLE "public"."order" ADD CONSTRAINT "order_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table user_informations
-- ----------------------------
ALTER TABLE "public"."user_informations" ADD CONSTRAINT "user_informations_pkey" PRIMARY KEY ("id");
