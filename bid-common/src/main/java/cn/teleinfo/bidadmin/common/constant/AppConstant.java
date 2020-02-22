package cn.teleinfo.bidadmin.common.constant;


/**
 * 系统常量
 *
 * @author Chill
 */
public interface AppConstant {

    /**
     * 应用版本
     */
    String APPLICATION_VERSION = "2.4.0";

    /**
     * 基础包
     */
    String BASE_PACKAGES = "cn.teleinfo.bidadmin";

    /**
     * 应用名前缀
     */
    String APPLICATION_NAME_PREFIX = "bid-";
    /**
     * 网关模块名称
     */
    String APPLICATION_GATEWAY_NAME = APPLICATION_NAME_PREFIX + "gateway";
    /**
     * 授权模块名称
     */
    String APPLICATION_AUTH_NAME = APPLICATION_NAME_PREFIX + "auth";

    /**
     * 首页模块名称
     */
    String APPLICATION_DESK_NAME = APPLICATION_NAME_PREFIX + "desk";

    /**
     * 模块名称
     */
    String APPLICATION_SOYBEAN_NAME = APPLICATION_NAME_PREFIX + "soybean";

    /**
     * 定时器模块名称
     */
    String APPLICATION_QUARTZ_NAME = APPLICATION_NAME_PREFIX + "quartz";
    /**
     * cms 系统
     */
    String APPLICATION_CMS_NAME = APPLICATION_NAME_PREFIX + "cms";
    /**
     * 系统模块名称
     */
    String APPLICATION_SYSTEM_NAME = APPLICATION_NAME_PREFIX + "system";
    /**
     * 用户模块名称
     */
    String APPLICATION_USER_NAME = APPLICATION_NAME_PREFIX + "user";

    /**
     * 区块链交互模块名称
     */
    String APPLICATION_BLOCKCHAIN_NAME = APPLICATION_NAME_PREFIX + "blockchain";

    /**
     * 百度人脸识别API模块名称
     */
    String APPLICATION_APP_NAME = APPLICATION_NAME_PREFIX + "app";

    /**
     * es rabbitmq 交互模块名称
     */
    String APPLICATION_ESRabbitMQ_NAME = APPLICATION_NAME_PREFIX + "es-rabbitmq";

    /**
     * 日志模块名称
     */
    String APPLICATION_LOG_NAME = APPLICATION_NAME_PREFIX + "log";

    /**
     * 监控模块名称
     */
    String APPLICATION_ADMIN_NAME = "admin";
    /**
     * 开发模块名称
     */
    String APPLICATION_DEVELOP_NAME =  "develop";
    /**
     * 资源模块名称
     */
    String APPLICATION_RESOURCE_NAME = "resource";
    /**
     * 测试模块名称
     */
    String APPLICATION_TEST_NAME = APPLICATION_NAME_PREFIX + "test";

    /**
     * 开发环境
     */
    String DEV_CODE = "dev";
    /**
     * 生产环境
     */
    String PROD_CODE = "prod";
    /**
     * 测试环境
     */
    String TEST_CODE = "test";

    /**
     * 代码部署于 linux 上，工作默认为 mac 和 Windows
     */
    String OS_NAME_LINUX = "LINUX";

}
