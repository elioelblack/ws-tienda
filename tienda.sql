--
-- Current Database: `tienda`
--

/*!40000 DROP DATABASE IF EXISTS `%s` */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `tienda` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `tienda`;

-- MySQL dump 10.13  Distrib 5.7.12, for Win32 (AMD64)
--
-- Host: localhost    Database: tienda
-- ------------------------------------------------------
-- Server version	5.5.5-10.3.20-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cliente`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `id_cliente` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK de la tabla',
  `primer_nombre` varchar(25) NOT NULL COMMENT 'Primer nombre',
  `segundo_nombre` varchar(25) DEFAULT NULL COMMENT 'Segundo nombre',
  `primer_apellido` varchar(25) NOT NULL COMMENT 'Primer apellido',
  `segundo_apellido` varchar(25) DEFAULT NULL COMMENT 'Segundo apellido',
  `flag_estado` int(11) NOT NULL DEFAULT 1 COMMENT 'Bandera de estado, 0=inactivo; 1=activo, mas estados superior a 1',
  `fecha_crea` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Campo auditoria',
  `ultimo_usuario` varchar(25) DEFAULT NULL COMMENT 'Campo auditoria',
  `dui_cliente` varchar(25) DEFAULT NULL COMMENT 'DUI del cliente',
  `nit_cliente` varchar(25) DEFAULT NULL COMMENT 'NIT del cliente',
  PRIMARY KEY (`id_cliente`),
  UNIQUE KEY `dui_cliente` (`dui_cliente`),
  UNIQUE KEY `nit_cliente` (`nit_cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oden`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oden` (
  `id_orden` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK de la tabla',
  `cliente_id` int(11) NOT NULL COMMENT 'FK con la tabla Cliente',
  `fecha_orden` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Fecha de la orden',
  `nota` varchar(255) DEFAULT NULL COMMENT 'Campo nota de la orden',
  `fecha_crea` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Campo auditoria',
  `ultimo_usuario` varchar(25) DEFAULT NULL COMMENT 'Campo auditoria',
  PRIMARY KEY (`id_orden`),
  KEY `cliente_id` (`cliente_id`),
  CONSTRAINT `oden_fk1` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id_cliente`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `orden_detalle`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orden_detalle` (
  `id_orden_detalle` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK de la tabla',
  `producto_id` int(11) NOT NULL COMMENT 'FK con la tabla producto',
  `orden_id` int(11) NOT NULL COMMENT 'FK tabla orden',
  `cantidad` int(11) NOT NULL COMMENT 'Cantidad del producto solicitado',
  `subtotal` double DEFAULT NULL COMMENT 'Subtotal del articulo',
  `iva` double DEFAULT NULL COMMENT 'Impuesto a aplicar',
  `descuento` double DEFAULT NULL COMMENT 'Descuento otorgado',
  `total` double DEFAULT NULL COMMENT 'Total por producto',
  `fecha_crea` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Campo auditoria',
  `ultimo_usuario` varchar(25) DEFAULT NULL COMMENT 'Campo auditoria',
  PRIMARY KEY (`id_orden_detalle`),
  KEY `producto_id` (`producto_id`),
  KEY `orden_id` (`orden_id`),
  CONSTRAINT `orden_detalle_fk1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id_producto`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `orden_detalle_fk2` FOREIGN KEY (`orden_id`) REFERENCES `oden` (`id_orden`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `producto`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `producto` (
  `id_producto` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK de la tabla',
  `nombre` varchar(25) NOT NULL COMMENT 'Nombre del producto',
  `categoria_id` int(11) NOT NULL COMMENT 'FK con la tabla categoria producto',
  `descripcion` varchar(100) DEFAULT NULL COMMENT 'Descripcion del producto',
  `costo` double NOT NULL COMMENT 'Costo del producto',
  `precio` double NOT NULL COMMENT 'Precio del producto',
  `flag_estado` int(11) NOT NULL DEFAULT 1 COMMENT 'Bandera de estado, 0=inactivo; 1=activo',
  `fecha_crea` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Campo auditoria',
  `ultimo_usuario` varchar(25) DEFAULT NULL COMMENT 'Campo auditoria',
  PRIMARY KEY (`id_producto`),
  KEY `categoria_id` (`categoria_id`),
  CONSTRAINT `producto_fk1` FOREIGN KEY (`categoria_id`) REFERENCES `producto_categoria` (`id_categoria`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `producto_categoria`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `producto_categoria` (
  `id_categoria` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK de la tabla',
  `nombre` varchar(25) NOT NULL COMMENT 'Nombre de la categoria',
  `descripcion` varchar(100) DEFAULT NULL COMMENT 'Descripcion de categoria',
  `flag_estado` int(11) NOT NULL DEFAULT 1 COMMENT 'Bandera de estado, 0=inactivo; 1=activo',
  `fecha_crea` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Campo auditoria',
  `ultimo_usuario` varchar(25) DEFAULT NULL COMMENT 'Campo auditoria',
  PRIMARY KEY (`id_categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-15 10:45:04
