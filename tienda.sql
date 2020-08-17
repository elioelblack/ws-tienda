/*
 Navicat Premium Data Transfer

 Source Server         : tienda
 Source Server Type    : MySQL
 Source Server Version : 50644
 Source Host           : localhost:3306
 Source Schema         : tienda

 Target Server Type    : MySQL
 Target Server Version : 50644
 File Encoding         : 65001

 Date: 17/08/2020 16:21:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cliente
-- ----------------------------
DROP TABLE IF EXISTS `cliente`;
CREATE TABLE `cliente`  (
  `id_cliente` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK de la tabla',
  `primer_nombre` varchar(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT 'Primer nombre',
  `segundo_nombre` varchar(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Segundo nombre',
  `primer_apellido` varchar(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT 'Primer apellido',
  `segundo_apellido` varchar(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Segundo apellido',
  `flag_estado` int(11) NOT NULL DEFAULT 1 COMMENT 'Bandera de estado, 0=inactivo; 1=activo, mas estados superior a 1',
  `fecha_crea` timestamp(0) NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Campo auditoria',
  `ultimo_usuario` varchar(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Campo auditoria',
  `dui_cliente` varchar(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'DUI del cliente',
  `nit_cliente` varchar(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'NIT del cliente',
  PRIMARY KEY (`id_cliente`) USING BTREE,
  UNIQUE INDEX `dui_cliente`(`dui_cliente`) USING BTREE,
  UNIQUE INDEX `nit_cliente`(`nit_cliente`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for orden
-- ----------------------------
DROP TABLE IF EXISTS `orden`;
CREATE TABLE `orden`  (
  `id_orden` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK de la tabla',
  `cliente_id` int(11) NOT NULL COMMENT 'FK con la tabla Cliente',
  `fecha_orden` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Fecha de la orden',
  `nota` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Campo nota de la orden',
  `fecha_crea` timestamp(0) NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Campo auditoria',
  `ultimo_usuario` varchar(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Campo auditoria',
  PRIMARY KEY (`id_orden`) USING BTREE,
  INDEX `cliente_id`(`cliente_id`) USING BTREE,
  CONSTRAINT `oden_fk1` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id_cliente`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for orden_detalle
-- ----------------------------
DROP TABLE IF EXISTS `orden_detalle`;
CREATE TABLE `orden_detalle`  (
  `id_orden_detalle` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK de la tabla',
  `producto_id` int(11) NULL DEFAULT NULL COMMENT 'FK con la tabla producto',
  `orden_id` int(11) NULL DEFAULT NULL COMMENT 'FK tabla orden',
  `cantidad` int(11) NOT NULL COMMENT 'Cantidad del producto solicitado',
  `subtotal` double NULL DEFAULT NULL COMMENT 'Subtotal del articulo',
  `iva` double NULL DEFAULT NULL COMMENT 'Impuesto a aplicar',
  `descuento` double NULL DEFAULT NULL COMMENT 'Descuento otorgado',
  `total` double NULL DEFAULT NULL COMMENT 'Total por producto',
  `fecha_crea` timestamp(0) NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Campo auditoria',
  `ultimo_usuario` varchar(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Campo auditoria',
  PRIMARY KEY (`id_orden_detalle`) USING BTREE,
  INDEX `producto_id`(`producto_id`) USING BTREE,
  INDEX `orden_id`(`orden_id`) USING BTREE,
  CONSTRAINT `orden_detalle_fk1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id_producto`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `orden_detallefk3` FOREIGN KEY (`orden_id`) REFERENCES `orden` (`id_orden`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for producto
-- ----------------------------
DROP TABLE IF EXISTS `producto`;
CREATE TABLE `producto`  (
  `id_producto` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK de la tabla',
  `nombre` varchar(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT 'Nombre del producto',
  `categoria_id` int(11) NOT NULL COMMENT 'FK con la tabla categoria producto',
  `descripcion` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Descripcion del producto',
  `costo` double NOT NULL COMMENT 'Costo del producto',
  `precio` double NOT NULL COMMENT 'Precio del producto',
  `flag_estado` int(11) NOT NULL DEFAULT 1 COMMENT 'Bandera de estado, 0=inactivo; 1=activo',
  `fecha_crea` timestamp(0) NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Campo auditoria',
  `ultimo_usuario` varchar(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Campo auditoria',
  PRIMARY KEY (`id_producto`) USING BTREE,
  INDEX `categoria_id`(`categoria_id`) USING BTREE,
  CONSTRAINT `producto_fk1` FOREIGN KEY (`categoria_id`) REFERENCES `producto_categoria` (`id_categoria`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for producto_categoria
-- ----------------------------
DROP TABLE IF EXISTS `producto_categoria`;
CREATE TABLE `producto_categoria`  (
  `id_categoria` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK de la tabla',
  `nombre` varchar(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT 'Nombre de la categoria',
  `descripcion` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Descripcion de categoria',
  `flag_estado` int(11) NOT NULL DEFAULT 1 COMMENT 'Bandera de estado, 0=inactivo; 1=activo',
  `fecha_crea` timestamp(0) NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Campo auditoria',
  `ultimo_usuario` varchar(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Campo auditoria',
  PRIMARY KEY (`id_categoria`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
