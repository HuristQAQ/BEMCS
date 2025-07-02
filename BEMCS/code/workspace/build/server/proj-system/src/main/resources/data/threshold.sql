-- 插入PM2.5阈值数据
INSERT INTO biz_threshold (data_type, min_value, max_value, unit, create_by, update_by)
VALUES (1, 80.00, 100.00, 'μg/m³', 'admin', 'admin');

-- 插入CO2浓度阈值数据
INSERT INTO biz_threshold (data_type, min_value, max_value, unit, create_by, update_by)
VALUES (2, 10.00, 30.00, 'ppm', 'admin', 'admin');

-- 插入温度阈值数据
INSERT INTO biz_threshold (data_type, min_value, max_value, unit, create_by, update_by)
VALUES (3, 20.00, 26.00, '℃', 'admin', 'admin');

-- 插入湿度阈值数据
INSERT INTO biz_threshold (data_type, min_value, max_value, unit, create_by, update_by)
VALUES (4, 40.00, 60.00, '%', 'admin', 'admin');

-- 插入光照强度阈值数据
INSERT INTO biz_threshold (data_type, min_value, max_value, unit, create_by, update_by)
VALUES (5, 300.00, 500.00, 'Lux', 'admin', 'admin');

-- 插入烟雾浓度阈值数据
INSERT INTO biz_threshold (data_type, min_value, max_value, unit, create_by, update_by)
VALUES (6, 5.00, 10.00, 'mg/m³', 'admin', 'admin');

-- 插入含氧量阈值数据
INSERT INTO biz_threshold (data_type, min_value, max_value, unit, create_by, update_by)
VALUES (7, 70.00, 80.00, '%', 'admin', 'admin'); 