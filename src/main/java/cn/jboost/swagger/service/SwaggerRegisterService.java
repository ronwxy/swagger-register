package cn.jboost.swagger.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/***
 * swagger注册服务，将注册信息存于内存与本地文件做持久化
 * @Author ronwxy
 * @Date 2019/6/28 11:04   
 */
@Component
public class SwaggerRegisterService implements ApplicationRunner {
    private static final Logger LOG = LoggerFactory.getLogger(SwaggerRegisterService.class);
    private ConcurrentHashMap<String, String> registerMap = new ConcurrentHashMap<>();

    public static final String STORE_KEY_PROJECT = "project";
    public static final String STORE_KEY_URL = "url";


    /**
     * 注册，存于内存与保存到文件持久化，便于重启后加载
     *
     * @param name 分组名称
     * @param url  /v2/api-docs 完整地址
     * @return
     */
    public File register(String name, String url) {
        File file = storeRegister(name, url);
        if(file != null) {
            registerMap.put(name, url);
        }
        return file;
    }

    public ConcurrentHashMap<String, String> getRegisterMap() {
        return registerMap;
    }

    /**
     * 启动时将注册信息从文件加载到内存
     *
     * @param
     * @return
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        loadRegisters(getBaseRegisterDir());
    }


    /**
     * 保存到用户目录下的swagger-registers文件夹下
     *
     * @param
     * @return
     */
    private File storeRegister(String module, String url) {
        File basePathDir = getBaseRegisterDir();
        if (basePathDir != null) {
            if (!basePathDir.exists()) {
                basePathDir.mkdir();
            }
            File toCreate = new File(basePathDir, String.format("%s.properties", module));

            try (FileWriter fw = new FileWriter(toCreate)) {
                fw.flush();
                try (FileReader reader = new FileReader(toCreate)) {
                    Properties p = new Properties();
                    p.load(reader);
                    p.setProperty(STORE_KEY_PROJECT, module);
                    p.setProperty(STORE_KEY_URL, url);
                    p.store(fw, "");
                } catch (IOException e) {

                }
                return toCreate;
            } catch (IOException e) {
                LOG.error(e.getLocalizedMessage());
            }
        }
        return null;
    }

    private void loadRegisters(File basePathDir) {
        if (basePathDir != null) {
            File[] files = basePathDir.listFiles();
            if (files != null && files.length > 0) {
                LOG.info("loading [{}] register docs", files.length);
                for (File f : files) {
                    Properties properties = new Properties();
                    try (FileInputStream fis = new FileInputStream(f)) {
                        properties.load(new InputStreamReader(fis, "UTF-8"));
                        String project = properties.getProperty(STORE_KEY_PROJECT);
                        String url = properties.getProperty(STORE_KEY_URL);
                        registerMap.put(project, url);
                        LOG.info("loaded register docs[{}={}]", project, url);
                    } catch (IOException e) {
                        LOG.error(e.getLocalizedMessage());
                    }
                }
            }
        }
    }

    private File getBaseRegisterDir() {
        File basePathDir = new File(System.getProperty("user.home"), "swagger-registers");
        return basePathDir;
    }
}
