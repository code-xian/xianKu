/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.27-log : Database - food
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`food` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `food`;

/*Table structure for table `administrators` */

DROP TABLE IF EXISTS `administrators`;

CREATE TABLE `administrators` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_token` varchar(32) NOT NULL,
  `admin_name` varchar(64) NOT NULL,
  `admin_username` varchar(32) NOT NULL,
  `admin_password` varchar(32) NOT NULL,
  `admin_authority` varchar(2) NOT NULL,
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `UNIQUE` (`admin_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

/*Data for the table `administrators` */

insert  into `administrators`(`admin_id`,`admin_token`,`admin_name`,`admin_username`,`admin_password`,`admin_authority`) values (1,'123','张三','zhangsan','123456','1'),(2,'111','李四','lisi','123456','0'),(5,'1578038079968','小七','xiaoqi','123456','0');

/*Table structure for table `chuku_detail` */

DROP TABLE IF EXISTS `chuku_detail`;

CREATE TABLE `chuku_detail` (
  `detail_id` varchar(32) NOT NULL COMMENT '详情id',
  `chuku_id` varchar(32) NOT NULL COMMENT '出库单据编号',
  `food_id` varchar(32) NOT NULL COMMENT '食品货号',
  `food_name` varchar(64) NOT NULL COMMENT '食品名称',
  `shelf_life` int(11) NOT NULL COMMENT '保质期',
  `food_unit` varchar(32) NOT NULL COMMENT '单位',
  `food_quantity` int(11) NOT NULL COMMENT '食品订单数量',
  `food_price` decimal(8,2) NOT NULL COMMENT '单价',
  `detail_price` decimal(8,2) NOT NULL COMMENT '金额',
  `detail_remarks` varchar(256) NOT NULL COMMENT '详情备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='出库详情单';

/*Data for the table `chuku_detail` */

/*Table structure for table `chuku_master` */

DROP TABLE IF EXISTS `chuku_master`;

CREATE TABLE `chuku_master` (
  `chuku_id` varchar(32) NOT NULL COMMENT '出库单据编号',
  `order_type` varchar(32) NOT NULL COMMENT '订单类型(普通订单)',
  `store_name` varchar(64) NOT NULL COMMENT '门店名字',
  `store_phone` varchar(32) NOT NULL,
  `store_address` varchar(128) NOT NULL,
  `chuku_abstract` varchar(128) NOT NULL COMMENT '摘要 (订单:销售【碧根果】等给【123】:【15017832568】--自动记账)',
  `chuku_money` decimal(8,2) NOT NULL COMMENT '总商品金额',
  `chuku_remarks` varchar(256) NOT NULL COMMENT '单据备注',
  `chuku_status` tinyint(3) NOT NULL COMMENT '单据状态 (已记账) ',
  `total_quantity` int(11) NOT NULL COMMENT '总商品数量',
  `chuku_date` date NOT NULL COMMENT '单据日期',
  `stock_name` varchar(32) NOT NULL COMMENT '出库仓库',
  `source_order` varchar(32) DEFAULT NULL COMMENT '来源订单',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`chuku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='出库单';

/*Data for the table `chuku_master` */

/*Table structure for table `fahuo_detail` */

DROP TABLE IF EXISTS `fahuo_detail`;

CREATE TABLE `fahuo_detail` (
  `detail_id` varchar(32) NOT NULL COMMENT '详情id',
  `fahuo_id` varchar(32) NOT NULL COMMENT '发货单据编号',
  `food_id` varchar(32) NOT NULL COMMENT '食品货号',
  `shelf_life` int(11) NOT NULL COMMENT '保质期',
  `food_quantity` int(11) NOT NULL COMMENT '食品订单数量',
  `food_unit` varchar(32) NOT NULL COMMENT '单位',
  `detail_remarks` varchar(256) NOT NULL COMMENT '详情备注',
  `food_name` varchar(64) NOT NULL COMMENT '食品名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='发货详情单';

/*Data for the table `fahuo_detail` */

/*Table structure for table `fahuo_master` */

DROP TABLE IF EXISTS `fahuo_master`;

CREATE TABLE `fahuo_master` (
  `fahuo_id` varchar(32) NOT NULL COMMENT '发货单据编号',
  `fahuo_stock` varchar(32) NOT NULL COMMENT '发货仓库',
  `fahuo_quantity` int(11) NOT NULL COMMENT '订单数量',
  `store_name` varchar(64) NOT NULL COMMENT '门店名字',
  `store_phone` varchar(32) NOT NULL COMMENT '门店电话',
  `store_address` varchar(128) NOT NULL COMMENT '门店地址(收货地址)',
  `fahuo_way` varchar(32) NOT NULL COMMENT '配送方式',
  `fahuo_remarks` varchar(256) NOT NULL COMMENT '单据备注',
  `fahuo_abstract` varchar(128) NOT NULL COMMENT '摘要',
  `fahuo_staus` tinyint(3) NOT NULL COMMENT '订单状态',
  `reviewer` varchar(32) NOT NULL COMMENT '审核人',
  `fahuo_date` date NOT NULL COMMENT '发货时间',
  `source_order` varchar(32) DEFAULT NULL COMMENT '来源订单',
  `fahuo_type` varchar(32) DEFAULT NULL COMMENT '发货类型( 销售发货 )',
  `sale_date` date NOT NULL COMMENT '单据日期',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`fahuo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='(待)发货单';

/*Data for the table `fahuo_master` */

/*Table structure for table `food_category` */

DROP TABLE IF EXISTS `food_category`;

CREATE TABLE `food_category` (
  `category_id` varchar(32) NOT NULL COMMENT '类目编号',
  `category_name` varchar(64) NOT NULL COMMENT '类目名字',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='类目表';

/*Data for the table `food_category` */

insert  into `food_category`(`category_id`,`category_name`,`create_time`,`update_time`) values ('001','糖果类','2020-02-16 18:08:53','2020-02-16 18:12:49'),('002','坚果类','2020-02-16 18:09:12','2020-02-16 18:09:12'),('003','饼干类','2020-02-16 18:09:17','2020-02-16 18:09:17'),('004','烟酒类','2020-02-16 18:10:18','2020-02-16 18:10:18'),('005','饮品类','2020-02-16 18:12:35','2020-02-16 18:12:35'),('006','膨化类','2020-02-16 18:13:00','2020-02-16 18:14:25'),('007','肉干类','2020-02-16 18:14:20','2020-02-16 18:14:20');

/*Table structure for table `food_info` */

DROP TABLE IF EXISTS `food_info`;

CREATE TABLE `food_info` (
  `food_id` varchar(32) NOT NULL COMMENT '食品货号',
  `food_name` varchar(64) NOT NULL COMMENT '食品名称',
  `food_price` decimal(8,2) NOT NULL COMMENT '单价',
  `food_description` varchar(64) DEFAULT NULL COMMENT '描述',
  `food_status` tinyint(3) NOT NULL COMMENT '食品上下架状态',
  `category_id` varchar(32) NOT NULL COMMENT '类目编号',
  `shelf_life` int(11) NOT NULL COMMENT '保质期',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`food_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='食品表';

/*Data for the table `food_info` */

insert  into `food_info`(`food_id`,`food_name`,`food_price`,`food_description`,`food_status`,`category_id`,`shelf_life`,`create_time`,`update_time`) values ('001','可口可乐','3.00','好喝的饮料',0,'005',36,'2020-02-17 15:22:33','2020-02-19 17:34:25'),('002','乐事薯片','10.00',NULL,0,'006',12,'2020-02-19 20:25:19','2020-02-19 20:25:31'),('003','雪碧','3.00',NULL,0,'005',36,'2020-02-19 20:26:09','2020-02-19 20:26:11');

/*Table structure for table `food_stock` */

DROP TABLE IF EXISTS `food_stock`;

CREATE TABLE `food_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `stock` int(11) NOT NULL COMMENT '库存',
  `food_id` varchar(32) NOT NULL COMMENT '食品id',
  `stock_id` varchar(32) NOT NULL COMMENT '仓库id',
  PRIMARY KEY (`id`),
  KEY `food_id` (`food_id`),
  KEY `stock_id` (`stock_id`),
  CONSTRAINT `food_stock_ibfk_1` FOREIGN KEY (`food_id`) REFERENCES `food_info` (`food_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `food_stock_ibfk_2` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`stock_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='食品库存表';

/*Data for the table `food_stock` */

insert  into `food_stock`(`id`,`stock`,`food_id`,`stock_id`) values (1,50,'001','001'),(2,50,'001','002'),(8,50,'001','003'),(10,100,'002','001'),(11,200,'003','001');

/*Table structure for table `purchase_detail` */

DROP TABLE IF EXISTS `purchase_detail`;

CREATE TABLE `purchase_detail` (
  `detail_id` varchar(32) NOT NULL COMMENT '详情id',
  `purchase_id` varchar(32) NOT NULL COMMENT '单据编号',
  `food_id` varchar(32) NOT NULL COMMENT '食品货号',
  `food_name` varchar(64) NOT NULL COMMENT '食品名称',
  `food_price` decimal(8,2) NOT NULL COMMENT '单价',
  `food_quantity` int(11) NOT NULL COMMENT '食品订单数量',
  `detail_remarks` varchar(256) NOT NULL COMMENT '详情备注',
  `stock_quantity` int(11) NOT NULL COMMENT '剩余库存',
  `food_unit` varchar(32) NOT NULL COMMENT '单位',
  `detail_price` decimal(8,2) NOT NULL COMMENT '金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购详情表';

/*Data for the table `purchase_detail` */

/*Table structure for table `purchase_master` */

DROP TABLE IF EXISTS `purchase_master`;

CREATE TABLE `purchase_master` (
  `purchase_id` varchar(32) NOT NULL COMMENT '单据编号',
  `supplier_name` varchar(32) NOT NULL COMMENT '采购厂商名称',
  `supplier_phone` varchar(32) NOT NULL COMMENT '采购厂商联系电话',
  `supplier_address` varchar(128) NOT NULL COMMENT '采购厂商地址',
  `stock_name` varchar(32) NOT NULL COMMENT '入库仓库',
  `purchase_remarks` varchar(256) NOT NULL COMMENT '单据备注',
  `purchase_status` tinyint(3) NOT NULL COMMENT '订单状态',
  `submission_date` date NOT NULL COMMENT '交货期限',
  `submission_way` varchar(32) NOT NULL COMMENT '交货方式',
  `reviewer` varchar(32) NOT NULL COMMENT '审核人',
  `purchase_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`purchase_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购订单表';

/*Data for the table `purchase_master` */

/*Table structure for table `ruku_detail` */

DROP TABLE IF EXISTS `ruku_detail`;

CREATE TABLE `ruku_detail` (
  `detail_id` varchar(32) NOT NULL COMMENT '详情id',
  `ruku_id` varchar(32) NOT NULL COMMENT '入库单据编号',
  `food_id` varchar(32) NOT NULL COMMENT '食品货号',
  `food_name` varchar(64) NOT NULL COMMENT '食品名称',
  `shelf_life` int(11) NOT NULL COMMENT '保质期',
  `food_unit` varchar(32) NOT NULL COMMENT '单位',
  `food_quantity` int(11) NOT NULL COMMENT '食品订单数量',
  `food_price` decimal(8,2) NOT NULL COMMENT '单价',
  `detail_price` decimal(8,2) NOT NULL COMMENT '金额',
  `detail_remarks` varchar(256) NOT NULL COMMENT '详情备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购入库详情表';

/*Data for the table `ruku_detail` */

/*Table structure for table `ruku_master` */

DROP TABLE IF EXISTS `ruku_master`;

CREATE TABLE `ruku_master` (
  `ruku_id` varchar(32) NOT NULL COMMENT '入库单据编号',
  `order_type` varchar(32) NOT NULL COMMENT '订单类型(普通订单)',
  `supplier_name` varchar(64) NOT NULL COMMENT '供应商名字',
  `supplier_phone` varchar(32) NOT NULL,
  `supplier_address` varchar(128) NOT NULL,
  `ruku_abstract` varchar(128) NOT NULL COMMENT '摘要 (订单:从【默认供应商】采购【碧根果】等:【15017832568】--自动记账)',
  `ruku_money` decimal(8,2) NOT NULL COMMENT '总商品金额',
  `ruku_remarks` varchar(256) NOT NULL COMMENT '单据备注',
  `ruku_status` tinyint(3) NOT NULL COMMENT '单据状态 (已记账) ',
  `total_quantity` int(11) NOT NULL COMMENT '总商品数量',
  `ruku_date` date NOT NULL COMMENT '单据日期',
  `stock_name` varchar(32) NOT NULL COMMENT '入库仓库',
  `source_order` varchar(32) DEFAULT NULL COMMENT '来源订单',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`ruku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购入库单';

/*Data for the table `ruku_master` */

/*Table structure for table `sale_detail` */

DROP TABLE IF EXISTS `sale_detail`;

CREATE TABLE `sale_detail` (
  `detail_id` varchar(32) NOT NULL COMMENT '详情id',
  `sale_id` varchar(32) NOT NULL COMMENT '单据编号',
  `food_id` varchar(32) NOT NULL COMMENT '食品货号',
  `food_name` varchar(64) NOT NULL COMMENT '食品名称',
  `food_price` decimal(8,2) NOT NULL COMMENT '单价',
  `food_quantity` int(11) NOT NULL COMMENT '食品订单数量',
  `detail_remarks` varchar(256) NOT NULL COMMENT '详情备注',
  `detail_price` decimal(8,2) NOT NULL COMMENT '金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售订单详情表';

/*Data for the table `sale_detail` */

/*Table structure for table `sale_master` */

DROP TABLE IF EXISTS `sale_master`;

CREATE TABLE `sale_master` (
  `sale_id` varchar(32) NOT NULL COMMENT '单据编号',
  `store_name` varchar(32) NOT NULL COMMENT '门店名字',
  `store_phone` varchar(32) NOT NULL COMMENT '门店电话',
  `store_address` varchar(128) NOT NULL COMMENT '门店地址',
  `submission_date` date NOT NULL COMMENT '交货期限',
  `submission_way` varchar(32) NOT NULL COMMENT '交货方式',
  `store_id` int(3) unsigned zerofill NOT NULL COMMENT '门店id',
  `store_fzr` varchar(32) DEFAULT NULL COMMENT '门店负责人',
  `stock_name` varchar(32) DEFAULT NULL COMMENT '发货仓库',
  `reviewer` varchar(32) NOT NULL COMMENT '审核人',
  `sale_remarks` varchar(256) NOT NULL COMMENT '单据备注',
  `sale_status` tinyint(3) NOT NULL COMMENT '订单状态',
  `purchase_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`sale_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售订单主表';

/*Data for the table `sale_master` */

insert  into `sale_master`(`sale_id`,`store_name`,`store_phone`,`store_address`,`submission_date`,`submission_way`,`store_id`,`store_fzr`,`stock_name`,`reviewer`,`sale_remarks`,`sale_status`,`purchase_amount`,`create_time`,`update_time`) values ('XXDD20191223001','天河区门店','15113413420','天河区凌塘村','2019-12-23','物流方式',000,NULL,'冷冻仓库','张三','此单加急',0,'500.00','2019-12-23 17:02:00','2019-12-23 17:02:00'),('XXDD20191223002','白云区门店','13413413420','白云区小云存','2019-12-23','物流方式',000,NULL,'干货仓库','张三','此单不急',0,'233.00','2019-12-23 18:03:37','2019-12-23 18:03:37'),('XXDD20191223003','番禺区门店','12312313454','番禺区鬼村','2019-12-23','物流',000,NULL,'好仓库','张三','此单随便',0,'33333.00','2019-12-23 18:27:42','2019-12-23 18:27:42');

/*Table structure for table `shouhuo_detail` */

DROP TABLE IF EXISTS `shouhuo_detail`;

CREATE TABLE `shouhuo_detail` (
  `detail_id` varchar(32) NOT NULL COMMENT '详情id',
  `shouhuo_id` varchar(32) NOT NULL COMMENT '收货单据编号',
  `food_id` varchar(32) NOT NULL COMMENT '食品货号',
  `shelf_life` int(11) NOT NULL COMMENT '保质期',
  `food_quantity` int(11) NOT NULL COMMENT '食品订单数量',
  `food_unit` varchar(32) NOT NULL COMMENT '单位',
  `detail_remarks` varchar(256) NOT NULL COMMENT '详情备注',
  `food_name` varchar(64) NOT NULL COMMENT '食品名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收货详情表';

/*Data for the table `shouhuo_detail` */

/*Table structure for table `shouhuo_master` */

DROP TABLE IF EXISTS `shouhuo_master`;

CREATE TABLE `shouhuo_master` (
  `shouhuo_id` varchar(32) NOT NULL COMMENT '收货单据编号',
  `sale_date` date NOT NULL COMMENT '单据日期',
  `shouhuo_stock` varchar(32) NOT NULL COMMENT '收货仓库',
  `shouhuo_quantity` int(11) NOT NULL COMMENT '订单数量',
  `supplier_name` varchar(64) NOT NULL COMMENT '供应商名字',
  `supplier_phone` varchar(32) NOT NULL COMMENT '供应商电话',
  `supplier_address` varchar(128) NOT NULL COMMENT '供应商地址',
  `shouhuo_way` varchar(32) NOT NULL COMMENT '配送方式(物流配送)',
  `shouhuo_remarks` varchar(256) NOT NULL COMMENT '单据备注',
  `shouhuo_abstract` varchar(128) NOT NULL COMMENT '摘要',
  `shouhuo_staus` tinyint(3) NOT NULL COMMENT '订单状态',
  `reviewer` varchar(32) NOT NULL COMMENT '审核人',
  `shouhuo_date` date NOT NULL COMMENT '预计收货时间',
  `source_order` varchar(32) DEFAULT NULL COMMENT '来源订单',
  `shouhuo_type` varchar(32) DEFAULT NULL COMMENT '收货类型( 销售发货 )',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`shouhuo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='(待)收货订单表';

/*Data for the table `shouhuo_master` */

/*Table structure for table `stock` */

DROP TABLE IF EXISTS `stock`;

CREATE TABLE `stock` (
  `stock_id` varchar(32) NOT NULL COMMENT '仓库编号',
  `stock_fzr` varchar(32) NOT NULL,
  `stock_type` varchar(32) NOT NULL,
  `stock_name` varchar(32) NOT NULL,
  `stock_note` varchar(256) DEFAULT NULL,
  `stock_status` tinyint(3) NOT NULL COMMENT '仓库可用状态',
  PRIMARY KEY (`stock_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='仓库表';

/*Data for the table `stock` */

insert  into `stock`(`stock_id`,`stock_fzr`,`stock_type`,`stock_name`,`stock_note`,`stock_status`) values ('001','一一','冷冻类','仓库一','目前没东西',0),('002','二二','干燥类','仓库二','这里面有东西',0),('003','小米','冷冻类','仓库三','这个仓库要满仓了',1),('004','小米','冷冻类','仓库三','这个仓库要满仓了',0);

/*Table structure for table `store` */

DROP TABLE IF EXISTS `store`;

CREATE TABLE `store` (
  `store_id` int(3) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '门店编号',
  `store_name` varchar(64) NOT NULL,
  `store_phone` varchar(32) NOT NULL,
  `store_fzr` varchar(32) NOT NULL,
  `store_address` varchar(128) NOT NULL,
  `store_note` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`store_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COMMENT='线下门店表';

/*Data for the table `store` */

insert  into `store`(`store_id`,`store_name`,`store_phone`,`store_fzr`,`store_address`,`store_note`) values (015,'天河区门店','13413413413','张三','天河区凌塘村','这店很牛p'),(016,'番禺区门店','13413413413','张三','天河区凌塘村','这店很牛p'),(017,'白云区门店','13413413413','张三','天河区凌塘村','这店很牛p'),(018,'越秀区门店','13413413413','张三','天河区凌塘村','这店很牛p'),(024,'深圳南山区门店','阿一','13413413425','深圳南山区','深圳第一家门店'),(025,'北京朝阳区门店','阿二','13413413413','北京朝阳区','北京第一家门店aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');

/*Table structure for table `supplier` */

DROP TABLE IF EXISTS `supplier`;

CREATE TABLE `supplier` (
  `supplier_id` int(3) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '供应商编号',
  `supplier_address` varchar(128) NOT NULL,
  `supplier_name` varchar(64) NOT NULL,
  `supplier_phone` varchar(32) NOT NULL,
  `supplier_fzr` varchar(32) NOT NULL,
  `supplier_type` varchar(16) NOT NULL,
  `supplier_note` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`supplier_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='供应商表';

/*Data for the table `supplier` */

insert  into `supplier`(`supplier_id`,`supplier_address`,`supplier_name`,`supplier_phone`,`supplier_fzr`,`supplier_type`,`supplier_note`) values (001,'美国加州','可口可乐公司','可可','1343135415','酒水饮料类','供应可口可乐和雪碧的大公司'),(002,'中国','哇哈哈','啊哈','13413413420','酒水饮料类','');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
