# Alibaba Seata 分布式事务示例

> spring cloud + nacos + feign + seata demo
>

## seata-core

```
seata 配置核心模块
```

## seata-commodity

```
商品模块
```

## seata-order

```
订单模块
```

## seata-stock

```
库存模块
```

## seata-api

```
网关模块
```

## seata-server

```
 seata server
 seata-server.sh -h nacos.blogyg.cn -p 8091 -m db -n 1 &   
```

## 服务全部启动正常后调用
```http request
http://127.0.0.1:5052/commodity/add

{
    "name":"young",
    "price":"1"
}
```

# 版本依赖关系

https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E

# jar  version

```
spring-cloud-alibaba-dependencies
2.2.6.RELEASE

spring-cloud-dependencies
Hoxton.SR9

nacos
1.3.0

seata
1.3.0
```

# seata-server config

- registry.conf

```editorconfig
registry {
# file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
type = "nacos"

     nacos {
     application = "seata-server"
     serverAddr = "nacos.blogyg.cn:9999"
                                   group = "DEFAULT_GROUP"
                                   namespace = ""
                                   cluster = "DEFAULT"
username = "nacos"
password = "nacos"
}
file {
name = "file.conf"
       }
       }

       config {
       # file、nacos 、apollo、zk、consul、etcd3
       type = "nacos"

       file {
       name = "file.conf"
              }

              nacos {
              serverAddr = "nacos.blogyg.cn:9999"
              group = "DEFAULT_GROUP"
                      namespace = ""
                      username = "nacos"
                      password = "nacos"
cluster = "DEFAULT"
}
}

```

- config.txt

```text
service.vgroupMapping.my_test_tx_group=default
service.enableDegrade=false
service.disableGlobalTransaction=false
store.mode=db
store.db.datasource=druid
store.db.dbType=mysql
store.db.driverClassName=com.mysql.jdbc.Driver
store.db.url=jdbc:mysql://www.blogyg.cn:3306/seata?useUnicode=true&rewriteBatchedStatements=true
store.db.user=root
store.db.password=123456
store.db.minConn=5
store.db.maxConn=30
store.db.globalTable=global_table
store.db.branchTable=branch_table
store.db.queryLimit=100
store.db.lockTable=lock_table
store.db.maxWait=5000
```

- seata-nacos.sh

```shell
#!/bin/sh
# Copyright 1999-2019 Seata.io Group.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at、
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

while getopts ":h:p:g:t:u:w:" opt
do
  case $opt in
  h)
    host=$OPTARG
    ;;
  p)
    port=$OPTARG
    ;;
  g)
    group=$OPTARG
    ;;
  t)
    tenant=$OPTARG
    ;;
  u)
    username=$OPTARG
    ;;
  w)
    password=$OPTARG
    ;;
  ?)
    echo " USAGE OPTION: $0 [-h host] [-p port] [-g group] [-t tenant] [-u username] [-w password] "
    exit 1
    ;;
  esac
done

if [ -z ${host} ]; then
    host=nacos.blogyg.cn
fi
if [ -z ${port} ]; then
    port=9999
fi
if [ -z ${group} ]; then
    group="DEFAULT_GROUP"
fi
if [ -z ${tenant} ]; then
    tenant=""
fi
if [ -z ${username} ]; then
    username="nacos"
fi
if [ -z ${password} ]; then
    password="nacos"
fi

nacosAddr=$host:$port
contentType="content-type:application/json;charset=UTF-8"

echo "set nacosAddr=$nacosAddr"
echo "set group=$group"

urlencode() {
  length="${#1}"
  i=0
  while [ $length -gt $i ]; do
    char="${1:$i:1}"
    case $char in
    [a-zA-Z0-9.~_-]) printf $char ;;
    *) printf '%%%02X' "'$char" ;;
    esac
    i=`expr $i + 1`
  done
}

failCount=0
tempLog=$(mktemp -u)
function addConfig() {
  dataId=`urlencode $1`
  content=`urlencode $2`
  curl -X POST -H "${contentType}" "http://$nacosAddr/nacos/v1/cs/configs?dataId=$dataId&group=$group&content=$content&tenant=$tenant&username=$username&password=$password" >"${tempLog}" 2>/dev/null
  if [ -z $(cat "${tempLog}") ]; then
    echo " Please check the cluster status. "
    exit 1
  fi
  if [ "$(cat "${tempLog}")" == "true" ]; then
    echo "Set $1=$2 successfully "
  else
    echo "Set $1=$2 failure "
    failCount=`expr $failCount + 1`
  fi
}

count=0
for line in $(cat $(dirname "$PWD")/config.txt | sed s/[[:space:]]//g); do
    count=`expr $count + 1`
	key=${line%%=*}
    value=${line#*=}
	addConfig "${key}" "${value}"
done

echo "========================================================================="
echo " Complete initialization parameters,  total-count:$count ,  failure-count:$failCount "
echo "========================================================================="

if [ ${failCount} -eq 0 ]; then
	echo " Init nacos config finished, please start seata-server. "
else
	echo " init nacos config fail. "
fi
```

# client config

```yaml
seata:
  enabled: true
  tx-service-group: my_test_tx_group
  application-id: seata-commodity
  config:
    type: nacos
    nacos:
      namespace: ""
      server-addr: "nacos.blogyg.cn:9999"
      group: "DEFAULT_GROUP"
      username: "nacos"
      password: "nacos"
      cluster: DEFAULT
  registry:
    type: nacos
    nacos:
      application: "seata-server"
      server-addr: "nacos.blogyg.cn:9999"
      group: "DEFAULT_GROUP"
      namespace: ""
      username: "nacos"
      password: "nacos"
      cluster: DEFAULT
```

- 数据源代理

> 此处展示使用 mybatis plus 方式交给 seata 代理，其他 orm 框架同理，只需要将 dataSourceProxy 交给 DataSourceTransactionManager 管理即可

```java
package com.young.seata.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import io.seata.rm.datasource.DataSourceProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * alibaba seata 配置类
 */
@Slf4j
@Configuration
public class SeataAutoConfig {

    @Value("${spring.application.name}")
    String applicationName;

    /**
     * autowired datasource config
     */
    @Autowired
    private DataSourceProperties dataSourceProperties;

    /**
     * init durid datasource
     *
     * @Return: druidDataSource  datasource instance
     */
    @Primary
    @Bean("druidDataSource")
    public DruidDataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(dataSourceProperties.getUrl());
        druidDataSource.setUsername(dataSourceProperties.getUsername());
        druidDataSource.setPassword(dataSourceProperties.getPassword());
        druidDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        druidDataSource.setInitialSize(0);
        druidDataSource.setMaxActive(180);
        druidDataSource.setMaxWait(60000);
        druidDataSource.setMinIdle(0);
        druidDataSource.setValidationQuery("Select 1 from DUAL");
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMinEvictableIdleTimeMillis(25200000);
        druidDataSource.setRemoveAbandoned(true);
        druidDataSource.setRemoveAbandonedTimeout(1800);
        druidDataSource.setLogAbandoned(true);
        return druidDataSource;
    }

    /**
     * init datasource proxy
     *
     * @Param: druidDataSource  datasource bean instance
     * @Return: DataSourceProxy  datasource proxy
     */
    @Bean
    public DataSourceProxy dataSourceProxy(DruidDataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSourceProxy dataSourceProxy) {
        return new DataSourceTransactionManager(dataSourceProxy);
    }

    /**
     * mybatis plus 需要加上此配置，否则无法使用 mybatis plus
     *
     * @param dataSource
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public MybatisSqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("druidDataSource") DataSource dataSource) {
        // 这里用 MybatisSqlSessionFactoryBean 代替了 SqlSessionFactoryBean，否则 MyBatisPlus 不会生效
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        return mybatisSqlSessionFactoryBean;
    }
}

```