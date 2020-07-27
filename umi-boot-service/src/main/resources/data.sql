-- 基础数据

INSERT INTO TB_DICT (ID, P_ID, LEVEL, DICT_NAME, DICT_CODE, DICT_DATA, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10000, 0, '0E', '性别', 'SYS.SEX', NULL, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_DICT (ID, P_ID, LEVEL, DICT_NAME, DICT_CODE, DICT_DATA, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10001, 10000, '0E.10000E', '男', NULL, 'man', 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_DICT (ID, P_ID, LEVEL, DICT_NAME, DICT_CODE, DICT_DATA, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10002, 10000, '0E.10000E', '女', NULL, 'woman', 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_DICT (ID, P_ID, LEVEL, DICT_NAME, DICT_CODE, DICT_DATA, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10100, 0, '0E', '请求类型', 'SYS.METHOD', NULL, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_DICT (ID, P_ID, LEVEL, DICT_NAME, DICT_CODE, DICT_DATA, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10101, 10100, '0E.10100E', 'GET', NULL, 'GET', 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_DICT (ID, P_ID, LEVEL, DICT_NAME, DICT_CODE, DICT_DATA, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10102, 10100, '0E.10100E', 'HEAD', NULL, 'HEAD', 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_DICT (ID, P_ID, LEVEL, DICT_NAME, DICT_CODE, DICT_DATA, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10103, 10100, '0E.10100E', 'POST', NULL, 'POST', 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_DICT (ID, P_ID, LEVEL, DICT_NAME, DICT_CODE, DICT_DATA, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10104, 10100, '0E.10100E', 'PUT', NULL, 'PUT', 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_DICT (ID, P_ID, LEVEL, DICT_NAME, DICT_CODE, DICT_DATA, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10105, 10100, '0E.10100E', 'PATCH', NULL, 'PATCH', 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_DICT (ID, P_ID, LEVEL, DICT_NAME, DICT_CODE, DICT_DATA, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10106, 10100, '0E.10100E', 'DELETE', NULL, 'DELETE', 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_DICT (ID, P_ID, LEVEL, DICT_NAME, DICT_CODE, DICT_DATA, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10107, 10100, '0E.10100E', 'OPTIONS', NULL, 'OPTIONS', 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_DICT (ID, P_ID, LEVEL, DICT_NAME, DICT_CODE, DICT_DATA, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10108, 10100, '0E.10100E', 'TRACE', NULL, 'TRACE', 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);

INSERT INTO TB_USER_INFO (ID, NICKNAME, SEX_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10000, '阿龙', 10001, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);

INSERT INTO TB_USER (ID, USERNAME, PASSWORD, USER_INFO_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10000, 'admin', '$2a$10$Km1thLqzLKttPmijquLCsu0WLRgm5WazJT5GekbbmmHTIuemD2fum', 10000, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);

INSERT INTO TB_AUTHORITY (ID, AUTHORITY_NAME, AUTHORITY_CODE, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10000, '系统管理员', 'ROLE_ADMIN', 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_AUTHORITY (ID, AUTHORITY_NAME, AUTHORITY_CODE, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10001, '系统管理员', 'ROLE_ADMIN', 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 0);

INSERT INTO TB_USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (10000, 10000);
INSERT INTO TB_USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (10000, 10001);

INSERT INTO TB_PERMISSION (ID, P_ID, LEVEL, PERMISSION_NAME, PERMISSION_PATH, PERMISSION_ICON, PERMISSION_SEQ, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10000, 0, '0E', '系统管理', '/system', 'setting', 100, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_PERMISSION (ID, P_ID, LEVEL, PERMISSION_NAME, PERMISSION_PATH, PERMISSION_ICON, PERMISSION_SEQ, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10001, 0, '0E', '系统管理', '/system', 'setting', 100, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 0);
INSERT INTO TB_PERMISSION (ID, P_ID, LEVEL, PERMISSION_NAME, PERMISSION_PATH, PERMISSION_ICON, PERMISSION_SEQ, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10010, 10000, '0E.10000E', '用户管理', '/system/user', NULL, 100, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_PERMISSION (ID, P_ID, LEVEL, PERMISSION_NAME, PERMISSION_PATH, PERMISSION_ICON, PERMISSION_SEQ, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10011, 10000, '0E.10000E', '菜单管理', '/system/permission', NULL, 99, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_PERMISSION (ID, P_ID, LEVEL, PERMISSION_NAME, PERMISSION_PATH, PERMISSION_ICON, PERMISSION_SEQ, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10012, 10000, '0E.10000E', '角色管理', '/system/authority', NULL, 98, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_PERMISSION (ID, P_ID, LEVEL, PERMISSION_NAME, PERMISSION_PATH, PERMISSION_ICON, PERMISSION_SEQ, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10100, 0, '0E', '数据管理', '/data', 'database', 99, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_PERMISSION (ID, P_ID, LEVEL, PERMISSION_NAME, PERMISSION_PATH, PERMISSION_ICON, PERMISSION_SEQ, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10110, 10100, '0E.10100E', '数据字典', '/data/dict', NULL, 100, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_PERMISSION (ID, P_ID, LEVEL, PERMISSION_NAME, PERMISSION_PATH, PERMISSION_ICON, PERMISSION_SEQ, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10111, 10100, '0E.10100E', '文件管理', '/data/file', NULL, 99, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);

INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10000, '/api/user/**', '新增用户', 10103, 10010, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10001, '/api/user/**', '查询用户', 10101, 10010, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10002, '/api/user/**', '修改用户', 10104, 10010, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10003, '/api/user/**', '删除用户', 10106, 10010, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10004, '/api/authority/**', '查询角色', 10101, 10010, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10100, '/api/permission/**', '新增菜单', 10103, 10011, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10101, '/api/permission/**', '查询菜单', 10101, 10011, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10102, '/api/permission/**', '修改菜单', 10104, 10011, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10103, '/api/permission/**', '删除菜单', 10106, 10011, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10200, '/api/resource/**', '新增菜单资源', 10103, 10011, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10201, '/api/resource/**', '查询菜单资源', 10101, 10011, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10202, '/api/resource/**', '修改菜单资源', 10104, 10011, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10203, '/api/resource/**', '删除菜单资源', 10106, 10011, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10300, '/api/authority/**', '新增角色', 10103, 10012, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10301, '/api/authority/**', '查询角色', 10101, 10012, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10302, '/api/authority/**', '修改角色', 10104, 10012, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10303, '/api/authority/**', '删除角色', 10106, 10012, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10400, '/api/dict/**', '新增字典', 10103, 10110, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10401, '/api/dict/**', '查询字典', 10101, 10110, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10402, '/api/dict/**', '修改字典', 10104, 10110, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10403, '/api/dict/**', '删除菜单', 10106, 10110, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);
INSERT INTO TB_RESOURCE (ID, RESOURCE_PATTERN, RESOURCE_DESC, METHOD_ID, PERMISSION_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, DATA_STATE) VALUES (10500, '/api/file/**', '查询文件', 10101, 10111, 'SYSTEM', '1970-01-01 08:00:00', 'SYSTEM', '1970-01-01 08:00:00', 2);

INSERT INTO TB_AUTHORITY_PERMISSION (AUTHORITY_ID, PERMISSION_ID) VALUES (10000, 10000);
INSERT INTO TB_AUTHORITY_PERMISSION (AUTHORITY_ID, PERMISSION_ID) VALUES (10000, 10001);
INSERT INTO TB_AUTHORITY_PERMISSION (AUTHORITY_ID, PERMISSION_ID) VALUES (10000, 10010);
INSERT INTO TB_AUTHORITY_PERMISSION (AUTHORITY_ID, PERMISSION_ID) VALUES (10000, 10011);
INSERT INTO TB_AUTHORITY_PERMISSION (AUTHORITY_ID, PERMISSION_ID) VALUES (10000, 10012);
INSERT INTO TB_AUTHORITY_PERMISSION (AUTHORITY_ID, PERMISSION_ID) VALUES (10000, 10100);
INSERT INTO TB_AUTHORITY_PERMISSION (AUTHORITY_ID, PERMISSION_ID) VALUES (10000, 10110);
INSERT INTO TB_AUTHORITY_PERMISSION (AUTHORITY_ID, PERMISSION_ID) VALUES (10000, 10111);