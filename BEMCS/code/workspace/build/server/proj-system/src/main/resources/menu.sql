-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('阈值管理', '0', '5', 'threshold', NULL, 1, 0, 'M', '0', '0', '', 'monitor', 'admin', sysdate(), '', null, '阈值管理菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('阈值列表', @parentId, '1', 'list', 'business/threshold/index', 1, 0, 'C', '0', '0', 'system:threshold:list', 'list', 'admin', sysdate(), '', null, '阈值列表菜单');

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('阈值查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:threshold:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('阈值新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:threshold:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('阈值修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:threshold:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('阈值删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:threshold:remove',       '#', 'admin', sysdate(), '', null, ''); 