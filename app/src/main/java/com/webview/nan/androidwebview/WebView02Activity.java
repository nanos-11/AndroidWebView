package com.webview.nan.androidwebview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * 示例二：
 * <p>
 * Android WebView自带的缓存机制有5种：
 * 浏览器 缓存机制
 * Application Cache 缓存机制
 * Dom Storage 缓存机制
 * Web SQL Database 缓存机制
 * Indexed Database 缓存机制
 * File System 缓存机制（H5页面新加入的缓存机制，虽然Android WebView暂时不支持，但会进行简单介绍）
 */
public class WebView02Activity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view02);

        mWebView = (WebView) findViewById(R.id.webview02);

        // Application Cache 缓存机制
        //applicationCache();
        // Dom Storage 缓存机制
        //domStorage();
        // Web SQL Database 缓存机制
        //webSQLDatabase();
        // IndexedDB 缓存机制
        //indexedDB();

    }

    /**
     * Application Cache 缓存机制
     * a. 原理
     * <p>
     * 以文件为单位进行缓存，且文件有一定更新机制（类似于浏览器缓存机制）
     * AppCache 原理有两个关键点：manifest 属性和 manifest 文件。
     * <p>
     * // 原理说明如下：
     * // AppCache 在首次加载生成后，也有更新机制。被缓存的文件如果要更新，需要更新 manifest 文件
     * // 因为浏览器在下次加载时，除了会默认使用缓存外，还会在后台检查 manifest 文件有没有修改（byte by byte)
     * 发现有修改，就会重新获取 manifest 文件，对 Section：CACHE MANIFEST 下文件列表检查更新
     * // manifest 文件与缓存文件的检查更新也遵守浏览器缓存机制
     * // 如用户手动清了 AppCache 缓存，下次加载时，浏览器会重新生成缓存，也可算是一种缓存的更新
     * // AppCache 的缓存文件，与浏览器的缓存文件分开存储的，因为 AppCache 在本地有 5MB（分 HOST）的空间限制
     * b. 特点
     * <p>
     * 方便构建Web App的缓存
     * <p>
     * 专门为 Web App离线使用而开发的缓存机制
     * c. 应用场景
     * <p>
     * 存储静态文件（如JS、CSS、字体文件）
     * <p>
     * 应用场景 同 浏览器缓存机制
     * 但AppCache 是对 浏览器缓存机制 的补充，不是替代。
     * d. 具体实现
     */
    private void applicationCache() {
        // 通过设置WebView的settings来实现
        WebSettings settings = mWebView.getSettings();

        String cacheDirPath = this.getFilesDir().getAbsolutePath() + "cache/";
        // 1. 设置缓存路径
        settings.setAppCachePath(cacheDirPath);
        // 2. 设置缓存大小
        settings.setAppCacheMaxSize(20 * 1024 * 1024);
        // 3. 开启Application Cache存储机制
        settings.setAppCacheEnabled(true);

        // 特别注意
        // 每个 Application 只调用一次 WebSettings.setAppCachePath() 和 WebSettings.setAppCacheMaxSize();
    }


    /**
     * 3. Dom Storage 缓存机制
     * a. 原理
     * 通过存储字符串的 Key - Value 对来提供
     * DOM Storage 分为 sessionStorage &  localStorage； 二者使用方法基本相同，区别在于作用范围不同：
     * a. sessionStorage：具备临时性，即存储与页面相关的数据，它在页面关闭后无法使用
     * b. localStorage：具备持久性，即保存的数据在页面关闭后也可以使用。
     * b. 特点
     * <p>
     * 存储空间大（ 5MB）：存储空间对于不同浏览器不同，如Cookies 才 4KB
     * 存储安全、便捷： Dom Storage 存储的数据在本地，不需要经常和服务器进行交互
     * 不像 Cookies每次请求一次页面，都会向服务器发送网络请求
     * c. 应用场景
     * <p>
     * 存储临时、简单的数据
     * <p>
     * 代替 将 不需要让服务器知道的信息 存储到 cookies 的这种传统方法
     * Dom Storage 机制类似于 Android 的 SharedPreference机制
     * d. 具体实现
     */
    private void domStorage() {
        // 通过设置 `WebView`的`Settings`类实现
        WebSettings settings = mWebView.getSettings();
        // 开启DOM storage
        settings.setDomStorageEnabled(true);
    }

    /**
     * Web SQL Database 缓存机制
     * <p>
     * a. 原理
     * 基于 SQL 的数据库存储机制
     * b. 特点
     * 充分利用数据库的优势，可方便对数据进行增加、删除、修改、查询
     * c. 应用场景
     * 存储适合数据库的结构化数据
     * d. 具体实现
     */
    private void webSQLDatabase() {
        // 通过设置WebView的settings实现
        WebSettings settings = mWebView.getSettings();
        // 设置缓存路径
        String cacheDirPath = this.getFilesDir().getAbsolutePath() + "cache/";
        settings.setDatabasePath(cacheDirPath);
        // 开启 数据库存储机制
        settings.setDatabaseEnabled(true);
    }


    /**
     * IndexedDB 缓存机制
     * <p>
     * a. 原理
     * <p>
     * 属于 NoSQL 数据库，通过存储字符串的 Key - Value 对来提供
     * <p>
     * 类似于 Dom Storage 存储机制 的key-value存储方式
     * b. 特点
     * c. 应用场景
     * <p>
     * 存储 复杂、数据量大的结构化数据
     */

    private void indexedDB() {
        // 通过设置WebView的settings实现
        WebSettings settings = mWebView.getSettings();

        // 只需设置支持JS就自动打开IndexedDB存储机制
        // Android 在4.4开始加入对 IndexedDB 的支持，只需打开允许 JS 执行的开关就好了。
        settings.setJavaScriptEnabled(true);
    }

    /**
     * . File System
     * <p>
     * a. 原理
     * <p>
     * 为 H5页面的数据 提供一个虚拟的文件系统
     * <p>
     * 可进行文件（夹）的创建、读、写、删除、遍历等操作，就像 Native App 访问本地文件系统一样
     * 虚拟的文件系统是运行在沙盒中
     * 不同 WebApp 的虚拟文件系统是互相隔离的，虚拟文件系统与本地文件系统也是互相隔离的。
     * 虚拟文件系统提供了两种类型的存储空间：临时 & 持久性：
     * <p>
     * 临时的存储空间：由浏览器自动分配，但可能被浏览器回收
     * 持久性的存储空间：需要显式申请；自己管理（浏览器不会回收，也不会清除内容）；存储空间大小通过配额管理，
     * 首次申请时会一个初始的配额，配额用完需要再次申请。
     * b. 特点
     * <p>
     * 可存储数据体积较大的二进制数据
     * 可预加载资源文件
     * 可直接编辑文件
     * c. 应用场景
     * <p>
     * 通过文件系统 管理数据
     * <p>
     * d. 具体使用
     * <p>
     * 由于 File System是 H5 新加入的缓存机制，所以Android WebView暂时不支持
     */
    private void fileSystem() {

    }


    /**
     * . 浏览器缓存机制
     * <p>
     * a. 原理
     * <p>
     * 根据 HTTP 协议头里的 Cache-Control（或 Expires）和 Last-Modified（或 Etag）等字段来控制文件缓存的机制
     * 下面详细介绍Cache-Control、Expires、Last-Modified & Etag四个字段
     * Cache-Control：用于控制文件在本地缓存有效时长
     * <p>
     * 如服务器回包：Cache-Control:max-age=600，则表示文件在本地应该缓存，且有效时长是600秒
     * （从发出请求算起）。在接下来600秒内，如果有请求这个资源，浏览器不会发出 HTTP 请求，而是直接使用本地缓存的文件。
     * Expires：与Cache-Control功能相同，即控制缓存的有效时间
     * <p>
     * Expires是 HTTP1.0 标准中的字段，Cache-Control 是 HTTP1.1 标准中新加的字段
     * 当这两个字段同时出现时，Cache-Control 优先级较高
     * Last-Modified：标识文件在服务器上的最新更新时间
     * <p>
     * 下次请求时，如果文件缓存过期，浏览器通过 If-Modified-Since 字段带上这个时间，发送给服务器，
     * 由服务器比较时间戳来判断文件是否有修改。如果没有修改，服务器返回304告诉浏览器继续使用缓存；
     * 如果有修改，则返回200，同时返回最新的文件。
     * Etag：功能同Last-Modified ，即标识文件在服务器上的最新更新时间。
     * <p>
     * 不同的是，Etag 的取值是一个对文件进行标识的特征字串。
     * 在向服务器查询文件是否有更新时，浏览器通过If-None-Match 字段把特征字串发送给服务器，
     * 由服务器和文件最新特征字串进行匹配，来判断文件是否有更新：没有更新回包304，有更新回包200
     * Etag 和 Last-Modified 可根据需求使用一个或两个同时使用。两个同时使用时，只要满足基中一个条件，就认为文件没有更新。
     * 常见用法是：
     * <p>
     * Cache-Control与 Last-Modified 一起使用；
     * Expires与 Etag一起使用；
     * 即一个用于控制缓存有效时间，一个用于在缓存失效后，向服务查询是否有更新
     * <p>
     * 特别注意：浏览器缓存机制 是 浏览器内核的机制，一般都是标准的实现
     * <p>
     * 即Cache-Control、 Last-Modified 、 Expires、 Etag都是标准实现，你不需要操心
     * b. 特点
     * <p>
     * 优点：支持 Http协议层
     * 不足：缓存文件需要首次加载后才会产生；浏览器缓存的存储空间有限，缓存有被清除的可能；缓存的文件没有校验。
     * 对于解决以上问题，可以参考手 Q 的离线包
     * c. 应用场景
     * <p>
     * 静态资源文件的存储，如JS、CSS、字体、图片等。
     * <p>
     * Android   Webview会将缓存的文件记录及文件内容会存在当前 app 的 data 目录中。
     * d. 具体实现
     * <p>
     * Android WebView内置自动实现，即不需要设置即实现
     * <p>
     * Android 4.4后的 WebView 浏览器版本内核：Chrome
     * 浏览器缓存机制 是 浏览器内核的机制，一般都是标准的实现
     */
    private void web() {

    }
}
