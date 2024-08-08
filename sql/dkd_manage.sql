CREATE TABLE tb_region (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '区域ID',
    NAME VARCHAR(255) NOT NULL COMMENT '区域名称',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    create_by VARCHAR(255) COMMENT '创建人',
    update_by VARCHAR(255) COMMENT '修改人',
    remark VARCHAR(255) COMMENT '备注'
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='区域表';

CREATE TABLE tb_partner (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '合作商ID',
    NAME VARCHAR(255) NOT NULL COMMENT '合作商名称',
    contact_person VARCHAR(255) COMMENT '联系人',
    contact_phone VARCHAR(20) COMMENT '联系电话',
    profit_share INT COMMENT '分成比例',
    account VARCHAR(255) COMMENT '账号',
    PASSWORD VARCHAR(255) COMMENT '密码',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    create_by VARCHAR(255) COMMENT '创建人',
    update_by VARCHAR(255) COMMENT '修改人',
    remark VARCHAR(255) COMMENT '备注'
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='合作商表';


CREATE TABLE tb_node (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '点位ID',
    NAME VARCHAR(255) NOT NULL COMMENT '点位名称',
    region_id INT COMMENT '所属区域ID',
    business_type INT COMMENT '商圈类型',
    partner_id INT COMMENT '合作商ID',
    address VARCHAR(255) COMMENT '详细地址',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    create_by VARCHAR(255) COMMENT '创建人',
    update_by VARCHAR(255) COMMENT '修改人',
    remark VARCHAR(255) COMMENT '备注',
    FOREIGN KEY (region_id) REFERENCES tb_region(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (partner_id) REFERENCES tb_partner(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='点位表';

INSERT INTO tb_region (NAME, create_by, update_by, remark)
VALUES ('成华区', 'admin', 'admin', '成都东部的一个行政区');
INSERT INTO tb_region (NAME, create_by, update_by, remark)
VALUES ('武侯区', 'admin', 'admin', '成都南部的一个行政区');

INSERT INTO tb_partner (NAME, contact_person, contact_phone, profit_share, account, PASSWORD, create_by, update_by, remark)
VALUES ('天科创造', '张三', '13800138000', 50, 'tiankechuangzao', 'password123', 'admin', 'admin', '一家位于成都市的高新技术企业');
INSERT INTO tb_partner (NAME, contact_person, contact_phone, profit_share, account, PASSWORD, create_by, update_by, remark)
VALUES ('盟升科创中心', '李四', '13900139000', 45, 'mengshengkexin', 'password456', 'admin', 'admin', '位于成都市的科技创新孵化器');

INSERT INTO tb_node (NAME, region_id, business_type, partner_id, address, create_by, update_by, remark)
VALUES ('天科创造产业基地', 1, 1, 1, '成都市成华区建设路1号', 'admin', 'admin', '天科创造在成华区的产业基地');
INSERT INTO tb_node (NAME, region_id, business_type, partner_id, address, create_by, update_by, remark)
VALUES ('盟升科创中心', 2, 2, 2, '成都市武侯区人民南路四段1号', 'admin', 'admin', '盟升科创中心在武侯区的办公地点');




