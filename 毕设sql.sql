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
  `food_quantity` int(11) NOT NULL COMMENT '食品订单数量',
  `food_price` decimal(8,2) NOT NULL COMMENT '单价',
  `detail_price` decimal(8,2) NOT NULL COMMENT '金额',
  `detail_remarks` varchar(256) DEFAULT NULL COMMENT '详情备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `stock_name` varchar(32) NOT NULL COMMENT '发货仓库',
  `stock_id` varchar(32) NOT NULL COMMENT '仓库id',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='出库详情单';

/*Data for the table `chuku_detail` */

insert  into `chuku_detail`(`detail_id`,`chuku_id`,`food_id`,`food_name`,`food_quantity`,`food_price`,`detail_price`,`detail_remarks`,`create_time`,`update_time`,`stock_name`,`stock_id`) values ('1583828512185583969','CKD20200310001','004','旺仔牛奶',5,'5.00','25.00','甜甜的牛奶','2020-03-10 16:21:52','2020-03-10 16:21:52','仓库一','001'),('1583977138366829650','CKD20200312001','003','雪碧',4,'3.00','12.00',NULL,'2020-03-12 09:38:58','2020-03-12 09:38:58','仓库一','001'),('1583977138476800311','CKD20200312001','004','旺仔牛奶',3,'5.00','15.00','甜甜的牛奶','2020-03-12 09:38:58','2020-03-12 09:38:58','仓库一','001');

/*Table structure for table `chuku_master` */

DROP TABLE IF EXISTS `chuku_master`;

CREATE TABLE `chuku_master` (
  `chuku_id` varchar(32) NOT NULL COMMENT '出库单据编号',
  `order_type` varchar(32) DEFAULT NULL COMMENT '订单类型(普通订单)',
  `store_name` varchar(64) NOT NULL COMMENT '门店名字',
  `store_phone` varchar(32) NOT NULL COMMENT '门店电话',
  `store_address` varchar(128) NOT NULL COMMENT '门店地址',
  `store_fzr` varchar(32) NOT NULL COMMENT '门店负责人',
  `chuku_money` decimal(8,2) NOT NULL COMMENT '总商品金额',
  `chuku_remarks` varchar(256) DEFAULT NULL COMMENT '单据备注',
  `chuku_status` tinyint(3) NOT NULL COMMENT '单据状态',
  `chuku_date` date NOT NULL COMMENT '单据日期',
  `source_order` varchar(32) NOT NULL COMMENT '来源订单',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `fahuo_way` varchar(32) NOT NULL COMMENT '配送方式',
  `reviewer` varchar(32) NOT NULL COMMENT '审核人',
  PRIMARY KEY (`chuku_id`,`chuku_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='出库单';

/*Data for the table `chuku_master` */

insert  into `chuku_master`(`chuku_id`,`order_type`,`store_name`,`store_phone`,`store_address`,`store_fzr`,`chuku_money`,`chuku_remarks`,`chuku_status`,`chuku_date`,`source_order`,`create_time`,`update_time`,`fahuo_way`,`reviewer`) values ('CKD20200310001',NULL,'白云区门店','13413413413','天河区凌塘村','张三','25.00','烦烦烦烦烦烦',11,'2020-03-11','FHD20200310001','2020-03-10 16:30:15','2020-03-11 09:58:36','物流','张三'),('CKD20200312001',NULL,'越秀区门店','13413413413','天河区凌塘村','张三','27.00','',10,'2020-03-19','FHD20200312001','2020-03-12 09:38:58','2020-03-12 09:39:41','物流','张三');

/*Table structure for table `fahuo_detail` */

DROP TABLE IF EXISTS `fahuo_detail`;

CREATE TABLE `fahuo_detail` (
  `detail_id` varchar(32) NOT NULL COMMENT '详情id',
  `fahuo_id` varchar(32) NOT NULL COMMENT '发货单据编号',
  `food_id` varchar(32) NOT NULL COMMENT '食品货号',
  `food_quantity` int(11) NOT NULL COMMENT '食品订单数量',
  `food_price` decimal(8,2) NOT NULL COMMENT '单价',
  `detail_remarks` varchar(256) DEFAULT NULL COMMENT '详情备注',
  `food_name` varchar(64) NOT NULL COMMENT '食品名称',
  `detail_price` decimal(8,2) NOT NULL COMMENT '金额',
  `stock_name` varchar(32) NOT NULL COMMENT '发货仓库',
  `stock_id` varchar(32) NOT NULL COMMENT '仓库id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`),
  KEY `fahuo_id` (`fahuo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='发货详情单';

/*Data for the table `fahuo_detail` */

insert  into `fahuo_detail`(`detail_id`,`fahuo_id`,`food_id`,`food_quantity`,`food_price`,`detail_remarks`,`food_name`,`detail_price`,`stock_name`,`stock_id`,`create_time`,`update_time`) values ('1583828425466854921','FHD20200310001','004',5,'5.00','甜甜的牛奶','旺仔牛奶','25.00','仓库一','001','2020-03-10 16:20:25','2020-03-10 16:20:25'),('1583833874028989964','FHD20200310002','004',3,'5.00','甜甜的牛奶','旺仔牛奶','15.00','仓库一','001','2020-03-10 17:51:14','2020-03-10 17:51:14'),('1583833874032572850','FHD20200310002','001',4,'3.00','好喝的饮料','可口可乐','12.00','仓库二','002','2020-03-10 17:51:14','2020-03-10 17:51:14'),('1583834737481256752','FHD20200310003','003',4,'3.00',NULL,'雪碧','12.00','仓库一','001','2020-03-10 18:05:37','2020-03-10 18:05:37'),('1583834865199130092','FHD20200310004','004',1,'5.00','甜甜的牛奶','旺仔牛奶','5.00','仓库一','001','2020-03-10 18:07:45','2020-03-10 18:07:45'),('1583976504262401647','FHD20200312001','003',4,'3.00',NULL,'雪碧','12.00','仓库一','001','2020-03-12 09:28:24','2020-03-12 09:28:24'),('1583976504262814217','FHD20200312001','004',3,'5.00','甜甜的牛奶','旺仔牛奶','15.00','仓库一','001','2020-03-12 09:28:24','2020-03-12 09:28:24');

/*Table structure for table `fahuo_master` */

DROP TABLE IF EXISTS `fahuo_master`;

CREATE TABLE `fahuo_master` (
  `fahuo_id` varchar(32) NOT NULL COMMENT '发货单据编号',
  `store_name` varchar(64) NOT NULL COMMENT '门店名字',
  `store_phone` varchar(32) NOT NULL COMMENT '门店电话',
  `store_address` varchar(128) NOT NULL COMMENT '门店地址(收货地址)',
  `store_fzr` varchar(32) NOT NULL COMMENT '门店负责人',
  `fahuo_way` varchar(32) NOT NULL COMMENT '配送方式',
  `fahuo_remarks` varchar(256) NOT NULL COMMENT '单据备注',
  `fahuo_status` tinyint(3) NOT NULL COMMENT '订单状态',
  `reviewer` varchar(32) NOT NULL COMMENT '审核人',
  `source_order` varchar(32) NOT NULL COMMENT '来源订单',
  `fahuo_type` varchar(32) DEFAULT NULL COMMENT '发货类型( 销售发货 )',
  `submission_date` date NOT NULL COMMENT '交货期限',
  `purchase_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`fahuo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='(待)发货单';

/*Data for the table `fahuo_master` */

insert  into `fahuo_master`(`fahuo_id`,`store_name`,`store_phone`,`store_address`,`store_fzr`,`fahuo_way`,`fahuo_remarks`,`fahuo_status`,`reviewer`,`source_order`,`fahuo_type`,`submission_date`,`purchase_amount`,`create_time`,`update_time`) values ('FHD20200310001','白云区门店','13413413413','天河区凌塘村','张三','物流','烦烦烦烦烦烦',10,'张三','XSDD20200310006',NULL,'2020-03-11','25.00','2020-03-10 16:20:25','2020-03-10 17:53:04'),('FHD20200310002','北京朝阳区门店','阿二','北京朝阳区','13413413413','空运','在在在在在在',11,'张三','XSDD20200310007',NULL,'2020-03-05','27.00','2020-03-10 17:51:14','2020-03-11 09:36:51'),('FHD20200310003','北京朝阳区门店','阿二','北京朝阳区','13413413413','空运','',12,'张三','XSDD20200310008',NULL,'2020-02-26','12.00','2020-03-10 18:05:37','2020-03-10 18:05:37'),('FHD20200310004','越秀区门店','13413413413','天河区凌塘村','张三','空运','',12,'张三','XSDD20200310009',NULL,'2020-03-19','5.00','2020-03-10 18:07:45','2020-03-10 18:07:45'),('FHD20200312001','越秀区门店','13413413413','天河区凌塘村','张三','物流','',10,'张三','XSDD20200312001',NULL,'2020-03-19','27.00','2020-03-12 09:28:24','2020-03-12 09:38:58');

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
  `purchase_price` decimal(8,2) DEFAULT NULL COMMENT '进价',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`food_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='食品表';

/*Data for the table `food_info` */

insert  into `food_info`(`food_id`,`food_name`,`food_price`,`food_description`,`food_status`,`category_id`,`shelf_life`,`purchase_price`,`create_time`,`update_time`) values ('001','可口可乐','3.00','好喝的饮料',0,'005',12,'2.00','2020-02-17 15:22:33','2020-03-14 10:34:04'),('002','乐事薯片','10.00','',0,'006',12,'5.00','2020-02-19 20:25:19','2020-03-14 10:34:06'),('003','雪碧','3.00','',0,'005',12,'2.00','2020-02-19 20:26:09','2020-03-14 10:34:08'),('004','旺仔牛奶','5.00','甜甜的牛奶',0,'005',12,'3.00','2020-03-10 14:27:24','2020-03-14 10:33:36'),('005','百岁山矿泉水','2.00','贵族矿泉水',0,'005',12,'1.00','2020-03-12 17:51:02','2020-03-14 10:33:40'),('006','营养快线','6.00','',0,'005',12,'4.00','2020-03-12 17:51:33','2020-03-14 10:33:47'),('007','七喜','3.00','',0,'005',12,'2.00','2020-03-12 17:52:03','2020-03-14 10:33:51'),('008','牛肉干','15.00','好吃的牛肉干',0,'007',12,'10.00','2020-03-14 10:55:43','2020-03-14 10:55:43');

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
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COMMENT='食品库存表';

/*Data for the table `food_stock` */

insert  into `food_stock`(`id`,`stock`,`food_id`,`stock_id`) values (1,32,'001','001'),(2,42,'001','002'),(8,19,'001','003'),(10,85,'002','001'),(11,187,'003','001'),(30,196,'004','001'),(31,4,'007','004'),(32,5,'008','004'),(34,100,'006','004');

/*Table structure for table `purchase_detail` */

DROP TABLE IF EXISTS `purchase_detail`;

CREATE TABLE `purchase_detail` (
  `detail_id` varchar(32) NOT NULL COMMENT '详情id',
  `purchase_id` varchar(32) NOT NULL COMMENT '单据编号',
  `food_id` varchar(32) NOT NULL COMMENT '食品货号',
  `food_name` varchar(64) NOT NULL COMMENT '食品名称',
  `food_price` decimal(8,2) NOT NULL COMMENT '进价',
  `food_quantity` int(11) NOT NULL COMMENT '食品订单数量',
  `detail_remarks` varchar(256) DEFAULT NULL COMMENT '详情备注',
  `stock_quantity` int(11) DEFAULT NULL COMMENT '剩余库存',
  `detail_price` decimal(8,2) NOT NULL COMMENT '金额',
  `stock_name` varchar(32) NOT NULL COMMENT '入货仓库',
  `stock_id` varchar(32) NOT NULL COMMENT '仓库id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购详情表';

/*Data for the table `purchase_detail` */

insert  into `purchase_detail`(`detail_id`,`purchase_id`,`food_id`,`food_name`,`food_price`,`food_quantity`,`detail_remarks`,`stock_quantity`,`detail_price`,`stock_name`,`stock_id`,`create_time`,`update_time`) values ('1584171168793460917','CGDD20200314001','001','可口可乐','2.00',10,'好喝的饮料',NULL,'20.00','仓库一','001','2020-03-14 15:32:48','2020-03-14 15:32:48'),('1584171168883583895','CGDD20200314001','002','乐事薯片','5.00',10,'',NULL,'50.00','仓库一','001','2020-03-14 15:32:49','2020-03-14 15:32:49'),('1584178290285967725','CGDD20200314002','001','可口可乐','2.00',14,'好喝的饮料',NULL,'28.00','仓库一','001','2020-03-14 17:31:30','2020-03-14 17:31:30'),('1584179266494235788','CGDD20200314003','007','七喜','2.00',4,'',NULL,'8.00','仓库四','004','2020-03-14 17:47:46','2020-03-14 17:47:46'),('1584179266504760140','CGDD20200314003','008','牛肉干','10.00',5,'好吃的牛肉干',NULL,'50.00','仓库四','004','2020-03-14 17:47:46','2020-03-14 17:47:46'),('1584179325029784223','CGDD20200314004','001','可口可乐','2.00',3,'好喝的饮料',NULL,'6.00','仓库三','003','2020-03-14 17:48:45','2020-03-14 17:48:45'),('1584179399397346135','CGDD20200314005','005','百岁山矿泉水','1.00',3,'贵族矿泉水',NULL,'3.00','仓库一','001','2020-03-14 17:49:59','2020-03-14 17:49:59'),('1584260336032407552','CGDD20200315001','006','营养快线','4.00',100,'',NULL,'400.00','仓库四','004','2020-03-15 16:18:56','2020-03-15 16:18:56');

/*Table structure for table `purchase_master` */

DROP TABLE IF EXISTS `purchase_master`;

CREATE TABLE `purchase_master` (
  `purchase_id` varchar(32) NOT NULL COMMENT '单据编号',
  `supplier_name` varchar(32) NOT NULL COMMENT '采购厂商名称',
  `supplier_phone` varchar(32) NOT NULL COMMENT '采购厂商联系电话',
  `supplier_address` varchar(128) NOT NULL COMMENT '采购厂商地址',
  `stock_name` varchar(32) DEFAULT NULL COMMENT '入库仓库',
  `purchase_remarks` varchar(256) DEFAULT NULL COMMENT '单据备注',
  `purchase_status` tinyint(3) NOT NULL COMMENT '订单状态',
  `submission_date` date NOT NULL COMMENT '交货期限',
  `submission_way` varchar(32) NOT NULL COMMENT '交货方式',
  `reviewer` varchar(32) NOT NULL COMMENT '审核人',
  `purchase_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
  `supplier_id` int(3) NOT NULL COMMENT '采购厂商id',
  `supplier_fzr` varchar(32) NOT NULL COMMENT '采购厂商负责人',
  `supplier_type` varchar(16) DEFAULT NULL COMMENT '采购厂商类型',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`purchase_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购订单表';

/*Data for the table `purchase_master` */

insert  into `purchase_master`(`purchase_id`,`supplier_name`,`supplier_phone`,`supplier_address`,`stock_name`,`purchase_remarks`,`purchase_status`,`submission_date`,`submission_way`,`reviewer`,`purchase_amount`,`supplier_id`,`supplier_fzr`,`supplier_type`,`create_time`,`update_time`) values ('CGDD20200314001','哇哈哈','13413413420','中国',NULL,'有有有有有有有有有有有有有有有有',10,'2020-06-01','物流','李四','70.00',2,'小中','酒水饮料类','2020-03-14 15:32:49','2020-03-15 16:28:02'),('CGDD20200314002','可口可乐公司','1343135415','美国加州',NULL,'啊啊啊啊啊',10,'2020-03-20','物流','张三','28.00',1,'小美','酒水饮料类','2020-03-14 17:31:30','2020-03-15 14:37:48'),('CGDD20200314003','哇哈哈','13413413420','中国',NULL,'哈哈哈哈哈',10,'2020-03-27','空运','张三','58.00',2,'小中','酒水饮料类','2020-03-14 17:47:46','2020-03-15 11:22:12'),('CGDD20200314004','可口可乐公司','1343135415','美国加州',NULL,'11111',10,'2020-03-25','空运','张三','6.00',1,'小美','酒水饮料类','2020-03-14 17:48:45','2020-03-15 11:13:27'),('CGDD20200314005','可口可乐公司','1343135415','美国加州',NULL,'',10,'2020-03-26','物流','张三','3.00',1,'小美','酒水饮料类','2020-03-14 17:49:59','2020-03-15 11:12:00'),('CGDD20200315001','哇哈哈','13413413420','中国',NULL,'灌灌灌灌灌',10,'2020-04-02','空运','张三','400.00',2,'小中','酒水饮料类','2020-03-15 16:18:56','2020-03-15 16:19:01');

/*Table structure for table `ruku_detail` */

DROP TABLE IF EXISTS `ruku_detail`;

CREATE TABLE `ruku_detail` (
  `detail_id` varchar(32) NOT NULL COMMENT '详情id',
  `ruku_id` varchar(32) NOT NULL COMMENT '入库单据编号',
  `food_id` varchar(32) NOT NULL COMMENT '食品货号',
  `food_name` varchar(64) NOT NULL COMMENT '食品名称',
  `food_quantity` int(11) NOT NULL COMMENT '食品订单数量',
  `food_price` decimal(8,2) NOT NULL COMMENT '进价',
  `detail_price` decimal(8,2) NOT NULL COMMENT '金额',
  `detail_remarks` varchar(256) DEFAULT NULL COMMENT '详情备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `stock_name` varchar(32) NOT NULL COMMENT '入库仓库',
  `stock_id` varchar(32) NOT NULL COMMENT '仓库id',
  PRIMARY KEY (`detail_id`,`ruku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购入库详情表';

/*Data for the table `ruku_detail` */

insert  into `ruku_detail`(`detail_id`,`ruku_id`,`food_id`,`food_name`,`food_quantity`,`food_price`,`detail_price`,`detail_remarks`,`create_time`,`update_time`,`stock_name`,`stock_id`) values ('1584260420851511367','RKD20200315001','006','营养快线',100,'4.00','400.00','','2020-03-15 16:20:20','2020-03-15 16:20:20','仓库四','004'),('1584260909273997873','RKD20200315002','001','可口可乐',10,'2.00','20.00','好喝的饮料','2020-03-15 16:28:29','2020-03-15 16:28:29','仓库一','001'),('1584260909276450950','RKD20200315002','002','乐事薯片',10,'5.00','50.00','','2020-03-15 16:28:29','2020-03-15 16:28:29','仓库一','001');

/*Table structure for table `ruku_master` */

DROP TABLE IF EXISTS `ruku_master`;

CREATE TABLE `ruku_master` (
  `ruku_id` varchar(32) NOT NULL COMMENT '入库单据编号',
  `order_type` varchar(32) DEFAULT NULL COMMENT '订单类型(普通订单)',
  `supplier_name` varchar(64) NOT NULL COMMENT '供应商名字',
  `supplier_phone` varchar(32) NOT NULL COMMENT '供应商电话',
  `supplier_address` varchar(128) NOT NULL COMMENT '供应商地址',
  `supplier_fzr` varchar(32) NOT NULL COMMENT '供应商负责人',
  `ruku_money` decimal(8,2) NOT NULL COMMENT '总商品金额',
  `ruku_remarks` varchar(256) DEFAULT NULL COMMENT '单据备注',
  `ruku_status` tinyint(3) NOT NULL COMMENT '单据状态 (已记账) ',
  `ruku_date` date NOT NULL COMMENT '单据日期',
  `source_order` varchar(32) NOT NULL COMMENT '来源订单',
  `reviewer` varchar(32) NOT NULL COMMENT '审核人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `shouhuo_way` varchar(32) NOT NULL COMMENT '运输方式',
  PRIMARY KEY (`ruku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购入库单';

/*Data for the table `ruku_master` */

insert  into `ruku_master`(`ruku_id`,`order_type`,`supplier_name`,`supplier_phone`,`supplier_address`,`supplier_fzr`,`ruku_money`,`ruku_remarks`,`ruku_status`,`ruku_date`,`source_order`,`reviewer`,`create_time`,`update_time`,`shouhuo_way`) values ('RKD20200315001',NULL,'哇哈哈','13413413420','中国','小中','400.00','灌灌灌灌灌',10,'2020-04-02','SHD20200315002','张三','2020-03-15 16:20:20','2020-03-15 16:22:10','空运'),('RKD20200315002',NULL,'哇哈哈','13413413420','中国','小中','70.00','有有有有有有有有有有有有有有有有',12,'2020-06-01','SHD20200315003','李四','2020-03-15 16:28:29','2020-03-15 16:28:29','物流');

/*Table structure for table `sale_detail` */

DROP TABLE IF EXISTS `sale_detail`;

CREATE TABLE `sale_detail` (
  `detail_id` varchar(32) NOT NULL COMMENT '详情id',
  `sale_id` varchar(32) NOT NULL COMMENT '单据编号',
  `food_id` varchar(32) NOT NULL COMMENT '食品id',
  `food_name` varchar(64) NOT NULL COMMENT '食品名称',
  `food_price` decimal(8,2) NOT NULL COMMENT '单价',
  `food_quantity` int(11) NOT NULL COMMENT '食品订单数量',
  `detail_remarks` varchar(256) DEFAULT NULL COMMENT '详情备注',
  `stock_id` varchar(32) NOT NULL COMMENT '仓库id',
  `stock_name` varchar(32) NOT NULL COMMENT '发货仓库',
  `detail_price` decimal(8,2) NOT NULL COMMENT '金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`),
  KEY `sale_id` (`sale_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售订单详情表';

/*Data for the table `sale_detail` */

insert  into `sale_detail`(`detail_id`,`sale_id`,`food_id`,`food_name`,`food_price`,`food_quantity`,`detail_remarks`,`stock_id`,`stock_name`,`detail_price`,`create_time`,`update_time`) values ('1583810916051232164','XSDD20200310001','001','可口可乐','3.00',2,'好喝的饮料','003','仓库三','6.00','2020-02-17 15:22:33','2020-02-19 17:34:25'),('1583810942088382356','XSDD20200310002','001','可口可乐','3.00',1,'好喝的饮料','003','仓库三','3.00','2020-02-17 15:22:33','2020-02-19 17:34:25'),('1583810942156191357','XSDD20200310002','001','可口可乐','3.00',1,'好喝的饮料','002','仓库二','3.00','2020-02-17 15:22:33','2020-02-19 17:34:25'),('1583810987326432731','XSDD20200310003','001','可口可乐','3.00',1,'好喝的饮料','001','仓库一','3.00','2020-02-17 15:22:33','2020-02-19 17:34:25'),('1583810987412300276','XSDD20200310003','002','乐事薯片','10.00',1,NULL,'001','仓库一','10.00','2020-02-19 20:25:19','2020-02-19 20:25:31'),('1583810987500678478','XSDD20200310003','003','雪碧','3.00',1,NULL,'001','仓库一','3.00','2020-02-19 20:26:09','2020-02-19 20:26:11'),('1583810987531935812','XSDD20200310003','001','可口可乐','3.00',1,'好喝的饮料','002','仓库二','3.00','2020-02-17 15:22:33','2020-02-19 17:34:25'),('1583810987565371830','XSDD20200310003','001','可口可乐','3.00',1,'好喝的饮料','003','仓库三','3.00','2020-02-17 15:22:33','2020-02-19 17:34:25'),('1583811317748762825','XSDD20200310004','001','可口可乐','3.00',1,'好喝的饮料','001','仓库一','3.00','2020-02-17 15:22:33','2020-02-19 17:34:25'),('1583811317838961597','XSDD20200310004','002','乐事薯片','10.00',1,NULL,'001','仓库一','10.00','2020-02-19 20:25:19','2020-02-19 20:25:31'),('1583811317880437865','XSDD20200310004','003','雪碧','3.00',1,NULL,'001','仓库一','3.00','2020-02-19 20:26:09','2020-02-19 20:26:11'),('1583811317901642760','XSDD20200310004','001','可口可乐','3.00',1,'好喝的饮料','002','仓库二','3.00','2020-02-17 15:22:33','2020-02-19 17:34:25'),('1583811318054429665','XSDD20200310004','001','可口可乐','3.00',1,'好喝的饮料','003','仓库三','3.00','2020-02-17 15:22:33','2020-02-19 17:34:25'),('1583811577914720646','XSDD20200310005','001','可口可乐','3.00',1,'好喝的饮料','001','仓库一','3.00','2020-02-17 15:22:33','2020-02-19 17:34:25'),('1583811577978504053','XSDD20200310005','002','乐事薯片','10.00',1,NULL,'001','仓库一','10.00','2020-02-19 20:25:19','2020-02-19 20:25:31'),('1583811578041802104','XSDD20200310005','003','雪碧','3.00',1,NULL,'001','仓库一','3.00','2020-02-19 20:26:09','2020-02-19 20:26:11'),('1583811578096236318','XSDD20200310005','001','可口可乐','3.00',1,'好喝的饮料','002','仓库二','3.00','2020-02-17 15:22:33','2020-02-19 17:34:25'),('1583811578125847085','XSDD20200310005','001','可口可乐','3.00',2,'好喝的饮料','003','仓库三','6.00','2020-02-17 15:22:33','2020-02-19 17:34:25'),('1583828411121240377','XSDD20200310006','004','旺仔牛奶','5.00',5,'甜甜的牛奶','001','仓库一','25.00','2020-03-10 14:27:24','2020-03-10 14:27:24'),('1583833866272384501','XSDD20200310007','004','旺仔牛奶','5.00',3,'甜甜的牛奶','001','仓库一','15.00','2020-03-10 14:27:24','2020-03-10 14:27:24'),('1583833866432299744','XSDD20200310007','001','可口可乐','3.00',4,'好喝的饮料','002','仓库二','12.00','2020-02-17 15:22:33','2020-02-19 17:34:25'),('1583834732391833612','XSDD20200310008','003','雪碧','3.00',4,NULL,'001','仓库一','12.00','2020-02-19 20:26:09','2020-02-19 20:26:11'),('1583834828499694380','XSDD20200310009','004','旺仔牛奶','5.00',1,'甜甜的牛奶','001','仓库一','5.00','2020-03-10 14:27:24','2020-03-10 14:27:24'),('1583976479975754682','XSDD20200312001','004','旺仔牛奶','5.00',3,'甜甜的牛奶','001','仓库一','15.00','2020-03-10 14:27:24','2020-03-10 14:27:24'),('1583976480324831248','XSDD20200312001','003','雪碧','3.00',4,NULL,'001','仓库一','12.00','2020-02-19 20:26:09','2020-02-19 20:26:11'),('1584179733411120536','XSDD20200314001','002','乐事薯片','10.00',5,'','001','仓库一','50.00','2020-02-19 20:25:19','2020-03-14 10:34:06'),('1584180409593202050','XSDD20200314002','001','可口可乐','3.00',1,'好喝的饮料','001','仓库一','3.00','2020-02-17 15:22:33','2020-03-14 10:34:04'),('1584180409717115930','XSDD20200314002','002','乐事薯片','10.00',1,'','001','仓库一','10.00','2020-02-19 20:25:19','2020-03-14 10:34:06'),('1584180409727375072','XSDD20200314002','003','雪碧','3.00',1,'','001','仓库一','3.00','2020-02-19 20:26:09','2020-03-14 10:34:08'),('1584180409738266338','XSDD20200314002','004','旺仔牛奶','5.00',1,'甜甜的牛奶','001','仓库一','5.00','2020-03-10 14:27:24','2020-03-14 10:33:36'),('1584180409747845310','XSDD20200314002','001','可口可乐','3.00',1,'好喝的饮料','002','仓库二','3.00','2020-02-17 15:22:33','2020-03-14 10:34:04'),('1584180409757117610','XSDD20200314002','001','可口可乐','3.00',1,'好喝的饮料','003','仓库三','3.00','2020-02-17 15:22:33','2020-03-14 10:34:04');

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
  `reviewer` varchar(32) NOT NULL COMMENT '审核人',
  `sale_remarks` varchar(256) NOT NULL COMMENT '单据备注',
  `sale_status` tinyint(3) NOT NULL COMMENT '订单状态',
  `purchase_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`sale_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售订单主表';

/*Data for the table `sale_master` */

insert  into `sale_master`(`sale_id`,`store_name`,`store_phone`,`store_address`,`submission_date`,`submission_way`,`store_id`,`store_fzr`,`reviewer`,`sale_remarks`,`sale_status`,`purchase_amount`,`create_time`,`update_time`) values ('XSDD20200310001','天河区门店','13413413413','天河区凌塘村','2020-03-26','物流',015,'张三','张三','',10,'6.00','2020-03-10 11:28:36','2020-03-10 11:28:40'),('XSDD20200310002','北京朝阳区门店','阿二','北京朝阳区','2020-03-28','空运',025,'13413413413','张三','111',10,'6.00','2020-03-10 11:29:02','2020-03-10 11:29:15'),('XSDD20200310003','白云区门店','13413413413','天河区凌塘村','2020-03-26','空运',017,'张三','张三','111',10,'22.00','2020-03-10 11:29:47','2020-03-10 11:29:54'),('XSDD20200310004','深圳南山区门店','阿一','深圳南山区','2020-03-11','空运',024,'13413413425','张三','',10,'22.00','2020-03-10 11:35:18','2020-03-10 11:35:28'),('XSDD20200310005','北京朝阳区门店','阿二','北京朝阳区','2020-03-11','物流',025,'13413413413','张三','333333333333333333333',10,'25.00','2020-03-10 11:39:38','2020-03-10 11:50:31'),('XSDD20200310006','白云区门店','13413413413','天河区凌塘村','2020-03-11','物流',017,'张三','张三','烦烦烦烦烦烦',10,'25.00','2020-03-10 16:20:11','2020-03-10 16:20:22'),('XSDD20200310007','北京朝阳区门店','阿二','北京朝阳区','2020-03-05','空运',025,'13413413413','张三','在在在在在在',10,'27.00','2020-03-10 17:51:06','2020-03-10 17:51:13'),('XSDD20200310008','北京朝阳区门店','阿二','北京朝阳区','2020-02-26','空运',025,'13413413413','张三','',10,'12.00','2020-03-10 18:05:32','2020-03-10 18:05:37'),('XSDD20200310009','越秀区门店','13413413413','天河区凌塘村','2020-03-19','空运',018,'张三','张三','',10,'5.00','2020-03-10 18:07:08','2020-03-10 18:07:45'),('XSDD20200312001','越秀区门店','13413413413','天河区凌塘村','2020-03-19','物流',018,'张三','张三','',10,'27.00','2020-03-12 09:28:00','2020-03-12 09:28:24'),('XSDD20200312002','白云区门店','13413413413','天河区凌塘村','2020-03-19','空运',017,'张三','张三','',12,'0.00','2020-03-12 10:07:08','2020-03-12 10:07:08'),('XSDD20200312003','白云区门店','13413413413','天河区凌塘村','2020-03-18','空运',017,'张三','张三','',12,'0.00','2020-03-12 10:08:53','2020-03-12 10:08:53'),('XSDD20200312004','白云区门店','13413413413','天河区凌塘村','2020-03-12','物流',017,'张三','张三','',12,'0.00','2020-03-12 10:10:00','2020-03-12 10:10:00'),('XSDD20200312005','越秀区门店','13413413413','天河区凌塘村','2020-03-12','物流',018,'张三','张三','',12,'0.00','2020-03-12 10:12:12','2020-03-12 10:12:12'),('XSDD20200312006','白云区门店','13413413413','天河区凌塘村','2020-02-26','空运',017,'张三','张三','',12,'0.00','2020-03-12 10:13:26','2020-03-12 10:13:26'),('XSDD20200314001','白云区门店','13413413413','天河区凌塘村','2020-03-19','空运',017,'张三','张三','',12,'50.00','2020-03-14 17:55:33','2020-03-14 17:55:33'),('XSDD20200314002','白云区门店','13413413413','天河区凌塘村','2020-03-18','物流',017,'张三','张三','',12,'27.00','2020-03-14 18:06:49','2020-03-14 18:06:49');

/*Table structure for table `shouhuo_detail` */

DROP TABLE IF EXISTS `shouhuo_detail`;

CREATE TABLE `shouhuo_detail` (
  `detail_id` varchar(32) NOT NULL COMMENT '详情id',
  `shouhuo_id` varchar(32) NOT NULL COMMENT '收货单据编号',
  `food_id` varchar(32) NOT NULL COMMENT '食品货号',
  `shelf_life` int(11) DEFAULT NULL COMMENT '保质期',
  `food_quantity` int(11) NOT NULL COMMENT '食品订单数量',
  `detail_remarks` varchar(256) DEFAULT NULL COMMENT '详情备注',
  `food_name` varchar(64) NOT NULL COMMENT '食品名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `food_price` decimal(8,2) NOT NULL COMMENT '进价',
  `stock_name` varchar(32) NOT NULL COMMENT '入库仓库',
  `stock_id` varchar(32) NOT NULL COMMENT '仓库id',
  `detail_price` decimal(8,2) NOT NULL COMMENT '金额',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收货详情表';

/*Data for the table `shouhuo_detail` */

insert  into `shouhuo_detail`(`detail_id`,`shouhuo_id`,`food_id`,`shelf_life`,`food_quantity`,`detail_remarks`,`food_name`,`create_time`,`update_time`,`food_price`,`stock_name`,`stock_id`,`detail_price`) values ('1584254268794721815','SHD20200315001','001',NULL,14,'好喝的饮料','可口可乐','2020-03-15 14:37:48','2020-03-15 14:37:48','2.00','仓库一','001','28.00'),('1584260341281487236','SHD20200315002','006',NULL,100,'','营养快线','2020-03-15 16:19:01','2020-03-15 16:19:01','4.00','仓库四','004','400.00'),('1584260882974556702','SHD20200315003','001',NULL,10,'好喝的饮料','可口可乐','2020-03-15 16:28:03','2020-03-15 16:28:03','2.00','仓库一','001','20.00'),('1584260882993970568','SHD20200315003','002',NULL,10,'','乐事薯片','2020-03-15 16:28:03','2020-03-15 16:28:03','5.00','仓库一','001','50.00');

/*Table structure for table `shouhuo_master` */

DROP TABLE IF EXISTS `shouhuo_master`;

CREATE TABLE `shouhuo_master` (
  `shouhuo_id` varchar(32) NOT NULL COMMENT '收货单据编号',
  `supplier_name` varchar(64) NOT NULL COMMENT '供应商名字',
  `supplier_phone` varchar(32) NOT NULL COMMENT '供应商电话',
  `supplier_address` varchar(128) NOT NULL COMMENT '供应商地址',
  `supplier_fzr` varchar(32) NOT NULL COMMENT '供应商负责人',
  `shouhuo_way` varchar(32) NOT NULL COMMENT '配送方式(物流配送)',
  `shouhuo_remarks` varchar(256) DEFAULT NULL COMMENT '单据备注',
  `shouhuo_status` tinyint(3) NOT NULL COMMENT '订单状态',
  `reviewer` varchar(32) NOT NULL COMMENT '审核人',
  `submission_date` date NOT NULL COMMENT '预计收货时间',
  `source_order` varchar(32) DEFAULT NULL COMMENT '来源订单',
  `shouhuo_type` varchar(32) DEFAULT NULL COMMENT '收货类型( 销售发货 )',
  `purchase_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`shouhuo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='(待)收货订单表';

/*Data for the table `shouhuo_master` */

insert  into `shouhuo_master`(`shouhuo_id`,`supplier_name`,`supplier_phone`,`supplier_address`,`supplier_fzr`,`shouhuo_way`,`shouhuo_remarks`,`shouhuo_status`,`reviewer`,`submission_date`,`source_order`,`shouhuo_type`,`purchase_amount`,`create_time`,`update_time`) values ('SHD20200315001','可口可乐公司','1343135415','美国加州','小美','物流','啊啊啊啊啊',11,'张三','2020-03-20','CGDD20200314002',NULL,'28.00','2020-03-15 14:37:48','2020-03-15 14:48:49'),('SHD20200315002','哇哈哈','13413413420','中国','小中','空运','灌灌灌灌灌',10,'张三','2020-04-02','CGDD20200315001',NULL,'400.00','2020-03-15 16:19:01','2020-03-15 16:20:20'),('SHD20200315003','哇哈哈','13413413420','中国','小中','物流','有有有有有有有有有有有有有有有有',10,'李四','2020-06-01','CGDD20200314001',NULL,'70.00','2020-03-15 16:28:03','2020-03-15 16:28:29');

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

insert  into `stock`(`stock_id`,`stock_fzr`,`stock_type`,`stock_name`,`stock_note`,`stock_status`) values ('001','一一','冷冻类','仓库一','目前没东西',0),('002','二二','干燥类','仓库二','这里面有东西',0),('003','小米','冷冻类','仓库三','这个仓库要满仓了',0),('004','小米','冷冻类','仓库四','这个仓库要满仓了',0);

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

insert  into `supplier`(`supplier_id`,`supplier_address`,`supplier_name`,`supplier_phone`,`supplier_fzr`,`supplier_type`,`supplier_note`) values (001,'美国加州','可口可乐公司','1343135415','小美','酒水饮料类','供应可口可乐和雪碧的大公司'),(002,'中国','哇哈哈','13413413420','小中','酒水饮料类','');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
