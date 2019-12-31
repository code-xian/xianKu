module.exports = {
    // chainWebpack: config => {
    //     // 修复HMR
    //     config.resolve.symlinks(true);
    //
    // },
    publicPath: "/",
    // outputDir:'dist',
    // assetsDir: 'static',

    devServer: {
        hot: true,
        port: 8099, // 端口号
        host: 'localhost',
        // proxy: {
        //     '/api': {
        //         target: 'http://localhost:8090', //对应自己的接口
        //         changeOrigin: true,
        //         ws: true,   // 是否启用websockets
        //         // pathRewrite: {
        //         //     '^/api': ''
        //         // }
        //     }
        // },
        disableHostCheck: true,//webpack4.0 开启热更新
    },
    css: {
        // 是否使用css分离插件 ExtractTextPlugin
        // extract: IS_PROD,
        // 开启 CSS source maps?
        sourceMap: false,
        // css预设器配置项
        loaderOptions: {
        },
        // 启用 CSS modules for all css / pre-processor files.
        modules: false,
    },

  }
