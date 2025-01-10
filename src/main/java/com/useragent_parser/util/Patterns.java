package com.useragent_parser.util;

public class Patterns {

    //   types for browsers
    private static final String INAPP = "inapp";

    //   types for devices
    private static final String MOBILE = "mobile";
    private static final String TABLET = "tablet";
    private static final String XR ="xr";
    private static final String SMARTTV = "smarttv";
    private static final String EMBEDDED = "embedded";
    private static final String CONSOLE = "console";
    private static final String WEARABLE = "wearable";

    public static final String[][] BROWSER_PATTERNS = {
    //  !!! {  regex,   default name,  №  name,   №  version,  default type , modifier  } !!!
            {"\\b(?:crmo|crios)\\/([\\w\\.]+)", "Mobile Chrome", null, "1", null, null },       // Chrome for Android/iOS
            {"edg(?:e|ios|a)?\\/([\\w\\.]+)", "Edge", null, "1", null, null },                  // Microsoft Edge
            {"(opera mini)\\/([-\\w\\.]+)", null, "1", "2", null, null },                       // Opera Mini
            {"(opera [mobiletab]{3,6})\\b.+version\\/([-\\w\\.]+)", null,   "1", "2", null, null },  // Opera Mobi/Tablet
            {"(opera)(?:.+version\\/|[\\/ ]+)([\\w\\.]+)", null,   "1", "2" , null, null },     // Opera
            {"opios[\\/ ]+([\\w\\.]+)", "Opera Mini", null,   "1", null, null },                // Opera Mini на iPhone
            {"\\bop(?:rg)?x\\/([\\w\\.]+)", "Opera GX", null, "1", null, null },                // Opera GX
            {"\\bopr\\/([\\w\\.]+)", "Opera", null, "1", null, null },                          // Opera Webkit
            {"\\bb[ai]*d(?:uhd|[ub]*[aekoprswx]{5,6})[\\/ ]?([\\w\\.]+)", "Baidu", null, "1", null, null },
            // Baidu
            {"\\b(?:mxbrowser|mxios|myie2)\\/?([-\\w\\.]*)\\b", "Maxthon", null, "1", null, null },           // Maxthon
            {"(kindle)\\/([\\w\\.]+)", null,  "1", "2", null,null },                                          // Kindle
            // Lunascape/Maxthon/Netfront/Jasmine/Blazer/Sleipnir
            {"(lunascape|maxthon|netfront|jasmine|blazer|sleipnir)[\\/ ]?([\\w\\.]+)?", null, "1", "2", null, null },
            // Avant/IEMobile/SlimBrowser/SlimBoat/Slimjet
            {"(avant|iemobile|slim(?:browser|boat|jet))[\\/ ]?([\\d\\.]+)?",null,"1", "2",null, null  },
            {"(?:ms|\\()(ie) ([\\w\\.]+)", null, "1", "2", null, null },                        // Internet Explore
            // Flock/RockMelt/Midori/Epiphany/Silk/Skyfire/Bolt/Iron/Iridium/PhantomJS/Bowser/QupZilla/Falkon
            {"(flock|rockmelt|midori|epiphany|silk|skyfire|ovibrowser|bolt|iron|vivaldi|iridium|phantomjs|bowser|qupzilla|falkon|rekonq|puffin|brave|whale(?!.+naver)|qqbrowserlite|duckduckgo|klar|helio|(?=comodo_)?dragon)\\/([-\\w\\.]+)",null,"1", "2", null, null },
            {"(heytap|ovi|115)browser\\/([\\d\\.]+)", null, "1", "2", null, null },             // HeyTap/Ovi/115
            {"(weibo)__([\\d\\.]+)", null, "1", "2", null, null },                              // Weibo
            {"quark(?:pc)?\\/([\\w\\.]+)", "Quark", null,"1",  null,null },                     // Quark
            {"ddg\\/([\\w\\.]+)", "DuckDuckGo", null,"1", null, null },                         // DuckDuckGo
            {"(?:\\buc? ?browser|(?:juc.+)ucweb)[\\/ ]?([\\w\\.]+)", "UCBrowser", null, "1", null,  null }, // UCBrowser
            {"microm.+\\bqbcore\\/([\\w\\.]+)", "WeChat", null,"1", null, null },               // WeChat Desktop for Windows Built-in Browser
            {"\\bqbcore\\/([\\w\\.]+).+microm", "WeChat", null, "1",null, null },
            {"micromessenger\\/([\\w\\.]+)", "WeChat", null, "1",null, null },                  // WeChat
            {"konqueror\\/([\\w\\.]+)", "Konqueror", null,"1",null, null },                     // Konqueror
            {"trident.+rv[: ]([\\w\\.]{1,9})\\b.+like gecko", "IE", null, "1", null, null },    // IE11
            {"ya(?:search)?browser\\/([\\w\\.]+)", "Yandex", null, "1", null, null },           // Yandex
            {"slbrowser\\/([\\w\\.]+)", "Smart Lenovo Browser", null, "1", null, null},         // Smart Lenovo Browser
            {"(avast|avg)\\/([\\w\\.]+)", null, "1", "2",null," Secure Browser" },              // Avast/AVG Secure Browser !
            {"\\bfocus\\/([\\w\\.]+)", "Firefox Focus", null, "1", null, null },                // Firefox Focus
            {"\\bopt\\/([\\w\\.]+)", "Opera Touch", null,"1", null, null },                     // Opera Touch
            {"coc_coc\\w+\\/([\\w\\.]+)", "Coc Coc", null,"1", null, null },                    // Coc Coc Browser
            {"dolfin\\/([\\w\\.]+)", "Dolphin", null, "1", null, null },                        // Dolphin
            {"coast\\/([\\w\\.]+)", "Opera Coast", null,"1", null, null },                      // Opera Coast
            {"miuibrowser\\/([\\w\\.]+)", "MIUI Browser", null,"1", null, null },               // MIUI Browser
            {"fxios\\/([\\w\\.-]+)", "Mobile Firefox", null,"1", null, null },                  // Firefox for iOS
            {"\\bqihoobrowser\\/?([\\w\\.]+)", "360", null, "1", null, null },                  // 360 Browser
            {"\\b(qq)\\/([\\w\\.]+)", null, "1", "2", null, "Browser" },
            // Oculus/Sailfish/HuaweiBrowser/VivoBrowser/PicoBrowser
            {"(oculus|sailfish|huawei|vivo|pico)browser\\/([\\w\\.]+)", null, "1", "2", null, " Browser",   },
            {"samsungbrowser\\/([\\w\\.]+)", "Samsung Internet", null, "1", null, null },        // Samsung Internet
            {"metasr[\\/ ]?([\\d\\.]+)", "Sogou Explorer", null,"1", null,  null },              // Sogou Explorer
            {"(sogou)mo\\w+\\/([\\d\\.]+)", "Sogou Mobile", null,"2", null,  null },             // Sogou Mobile
            {"(electron)\\/([\\w\\.]+) safari", null, "1", "2", null,  null },                   // Electron-based App
            {"(tesla)(?: qtcarbrowser|\\/(20\\d\\d\\.[-\\w\\.]+))", null,"1", "2", null, null }, // Tesla
            {"m?(qqbrowser|2345(?=browser|chrome|explorer))\\w*[\\/ ]?v?([\\w\\.]+)", null,"1", "2", null, null}, // QQ/2345
            {"(lbbrowser|rekonq)", null, "1", null, null, null},                                 // LieBao Browser/Rekonq
            {"ome\\/([\\w\\.]+) \\w* ?(iron) saf", null,"2", "1", null, null },                  // Iron
            {"ome\\/([\\w\\.]+).+qihu (360)[es]e",null, "2", "1", null, null },                  // 360
            // Facebook App for iOS & Android
            {"((?:fban\\/fbios|fb_iab\\/fb4a)(?!.+fbav)|;fbav\\/([\\w\\.]+);)", "Facebook", null, "2", INAPP, null},
            // Klarna Shopping Browser for iOS & Android
            {"(Klarna)\\/([\\w\\.]+)", null, "1",  "2", INAPP , null },
            {"(kakao(?:talk|story))[\\/ ]([\\w\\.]+)", null, "1", "2", INAPP, null },            // Kakao App
            {"(naver)\\(.*?(\\d+\\.[\\w\\.]+).*\\)", null, "1", "2", INAPP, null },              // Naver InApp
            {"safari (line)\\/([\\w\\.]+)", null,"1", "2", INAPP , null },                       // Line App for iOS
            {"\\b(line)\\/([\\w\\.]+)\\/iab", null, "1", "2", INAPP,null },                      // Line App for Android
            {"(alipay)client\\/([\\w\\.]+)", null,"1", "2", INAPP, null },                       // Alipay
            {"(twitter)(?:and| f.+e\\/([\\w\\.]+))", null,"1", "2", INAPP, null },               // Twitter
            {"(instagram|snapchat)[\\/ ]([\\-\\w\\.]+)", null,"1", "2", INAPP, null},            // Instagram/Snapchat
            {"\\bgsa\\/([\\w\\.]+) .*safari\\/", "GSA", null, "1", INAPP,  null  },              // Google Search Appliance on iOS
            {"musical_ly(?:.+app_?version\\/|_)([\\w\\.]+)", "TikTok", null, "1", INAPP, null }, // TikTok
            {"\\[(linkedin)app\\]", null, "1", null,  INAPP,  null },
             // LinkedIn App for iOS & Android
            {"(chromium)[\\/ ]([\\-\\w\\.]+)", null,"1", "2", null, null },                      // Chromium
            {"headlesschrome(?:\\/([\\w\\.]+)| )", "Chrome Headless", null,"1", null,  null },   // Chrome Headless
            {"wv\\).+(chrome)\\/([\\w\\.]+)", "Chrome WebView", null, "2", null, null },         // Chrome WebView
            {"droid.+ version\\/([\\w\\.]+)\\b.+(?:mobile safari|safari)", "Android Browser", null,"1", null,  null },   // Android Browser
            {"chrome\\/([\\w\\.]+) mobile", "Mobile Chrome", null, "1", null, null },                                    // Chrome Mobile
            {"(chrome|omniweb|arora|[tizenoka]{5} ?browser)\\/v?([\\w\\.]+)",null, "1", "2" , null, null },              // Chrome/OmniWeb/Arora/Tizen/Nokia
            {"version\\/([\\w\\.\\,]+) .*mobile(?:\\/\\w+ | ?)safari", "Mobile Safari", null, "1", null,  null },        // Safari Mobile
            {"iphone .*mobile(?:\\/\\w+ | ?)safari", "Mobile Safari", null,  null, null, null },             // Safari Mobile
            {"version\\/([\\w\\.\\,]+) .*?(safari)", null,"2", "1", null, null },                            // Safari
            {"webkit.+?(mobile ?safari|safari)(\\/[\\w\\.]+)", null, "1", null, null, "versionDefault" },    // Safari < 3.0
            {"(webkit|khtml)\\/([\\w\\.]+)", null,"1", "2", null, null },
            {"(?:(mobile|tablet);.*(firefox)\\/([\\w\\.-]+))", "Mobile Firefox", null, "3", null, null },    // Firefox Mobile
            {"(navigator|netscape\\d?)\\/([\\-\\w\\.]+)", "Netscape", null,"2", null, null },                // Netscape
            {"(wolvic|librewolf)\\/([\\w\\.]+)",null,"1", "2", null, null },                                 // Wolvic/LibreWolf
            {"mobile vr; rv:([\\w\\.]+)\\).+firefox", "Firefox Reality", null,"1", null, null },             // Firefox Reality
            {"ekiohf.+(flow)\\/([\\w\\.]+)",null,"1", "2", null,  null },                                    // Flow
            {"(swiftfox)",null, "1", null, null, null },                                                     // Swiftfox
            // IceDragon/Iceweasel/Camino/Chimera/Fennec/Maemo/Minimo/Conkeror
            {"(icedragon|iceweasel|camino|chimera|fennec|maemo browser|minimo|conkeror)[\\/ ]?([\\w\\.\\+]+)",null,"1", "2", null, null,   },
            // Firefox/SeaMonkey/K-Meleon/IceCat/IceApe/Firebird/Phoenix
            {"(seamonkey|k-meleon|icecat|iceape|firebird|phoenix|palemoon|basilisk|waterfox)\\/([-\\w\\.]+)$",null, "1", "2", null, null,   },
            {"(firefox)\\/([\\w\\.]+)", null, "1", "2", null, null },                            // Other Firefox-based
            {"(mozilla)\\/([\\w\\.]+) .+rv\\:.+gecko\\/\\d+", null,"1", "2", null, null },       // Mozilla
            // Polaris/Lynx/Dillo/iCab/Doris/Amaya/w3m/NetSurf/Obigo/Mosaic/Go/ICE/UP.Browser
            {"(polaris|lynx|dillo|icab|doris|amaya|w3m|netsurf|obigo|mosaic|(?:go|ice|up)[\\. ]?browser)[-\\/ ]?v?([\\w\\.]+)",null,"1", "2", null,  "replaceAll"  },
            {"\\b(links) \\(([\\w\\.]+)",  null, "1", "2", null, "replaceAll" },                 // Links
            {"(cobalt)/([\\w\\.]+)",null,"1", "2", null, "cleanedVersion" },                     // Cobalt
    };

    public  static final String[][] CPU_PATTERNS = {
    // !!!! { regex,  default architecture, word for replace} !!!
            // AMD64 (x64)
            {"\\b(?:(amd|x|x86[-_]?|wow|win)64)\\b", "amd64", null },
            // IA32 (Quicktime or x86)
            {"(ia32(?=;))", "ia32", null },
            {"\\b((?:i[346]|x)86)\\b", "ia32", null },
            // ARM64
            {"\\b(aarch64|arm(v?8e?l?|_?64))\\b", "arm64", null },
            // ARMHF
            {"\\b(arm(?:v[67])?ht?n?[fl]p?)\\b", "armhf", null},
            // PocketPC mistakenly identified as PowerPC (Windows CE or Mobile)
            {"windows (ce|mobile); ppc;", "arm", null },
            // PowerPC
            {"((?:ppc|powerpc)(?:64)?)(?: mac|;|\\))", null, "ower"},
            // SPARC
            {"(sun4\\w)[;\\)]", "sparc", null },
            // IA64, 68K, ARM/64, AVR/32, IRIX/64, MIPS/64, SPARC/64, PA-RISC
            {"((?:avr32|ia64(?=;))|68k(?=\\))|\\barm(?=v(?:[1-7]|[5-7]1)l?|;|eabi)|(?=atmel )avr|(?:irix|mips|sparc)(?:64)?\\b|pa-risc)", null, null }
    };

    public  static final String[][] ENGINE_PATTERNS = {
    // !!!! { regex,   default name,   № for name,   № for version} !!!
            //  EdgeHTML
            {"windows.+ edge\\/([\\w\\.]+)", "EdgeHTML", null, "1" },
            // ArkWeb
            {"(arkweb)\\/([\\w\\.]+)", null, "1", "2"},
            // Blink
            {"webkit\\/537\\.36.+chrome\\/(?!27)([\\w\\.]+)", "Blink", null, "1"},
            // Presto
            {"(presto)\\/([\\w\\.]+)", null, "1", "2"},
            // WebKit/Trident/NetFront/NetSurf/Amaya/Lynx/w3m/Goanna/Servo
            {"(webkit|trident|netfront|netsurf|amaya|lynx|w3m|goanna|servo)\\/([\\w\\.]+)", null, "1", "2"},
            // Flow
            { "ekioh(flow)/([\\w\\.]+)", null,  "1", "2"},
            // KHTML/Tasman/Links
            {"(khtml|tasman|links)[\\/ ]\\(?([\\w\\.]+)", null, "1", "2"},
            // iCab
            {"(icab)[\\/ ]([23]\\.[\\d\\.]+)", null, "1", "2"},
            // LibWeb
            {"\\b(libweb)", null, "1", null},
            // Gecko
            {"rv\\:([\\w\\.]{1,9})\\b.+(gecko)", null, "2", "1"}
    };

    public  static final String[][] OS_PATTERNS = {
            // !!!! {  regex, default name,  № for name,  № for version, modifier for version } !!!

            // Windows (iTunes)

            {"microsoft (windows) (vista|xp)", null, "1", "2", null },
            // Windows Phone
            {"(windows (?:phone(?: os)?|mobile))[/ ]?([\\d\\.\\w ]*)", null,  "1", "2", "windowsVersionMap"},
            // Windows RT
            {"windows nt 6\\.2; (arm)", "Windows", null, "1", "windowsVersionMap"},
            {"windows[ /]?([ntce\\d\\. ]+\\w)(?!.+xbox)", "Windows",  null, "1", "windowsVersionMap"},
            {"(?:win(?=3|9|n)|win 9x )([nt\\d\\.]+)", "Windows",  null, "1", "windowsVersionMap"},
            // iOS
            {"ip[honead]{2,4}\\b(?:.*os ([\\w]+) like mac|; opera)", "iOS", null, "1", "replaceAll"},
            {"(?:ios;fbsv\\/|iphone.+ios[\\/ ])([\\d\\.]+)", "iOS", null, "1", "replaceAll"},
            {"cfnetwork\\/.+darwin", "iOS", null, null, null},
            // Mac OS
            {"(mac os x) ?([\\w\\. ]*)", "macOS", null, "2", "replaceAll"},
            {"(macintosh|mac_powerpc\\b)(?!.+haiku)", "macOS", null, null, null},
            // Google Chromecast, Android-based
            {"android ([\\d\\.]+).*crkey", "Chromecast Android", null, "1", null},
            // Google Chromecast, Fuchsia-based
            {"fuchsia.*crkey\\/([\\d\\.]+)", "Chromecast Fuchsia", null, "1", null},
            // Google Chromecast, Linux-based Smart Speaker
            {"crkey\\/([\\d\\.]+).*devicetype\\/smartspeaker", "Chromecast SmartSpeaker", null, "1", null},
            // Google Chromecast, Legacy Linux-based
            {"linux.*crkey\\/([\\d\\.]+)", "Chromecast Linux", null, "1", null},
            // Google Chromecast, unknown
            {"crkey\\/([\\d\\.]+)", "Chromecast", null, "1", null},
            // Android-x86/HarmonyOS
            {"droid ([\\w\\.]+)\\b.+(android[- ]x86|harmonyos)", null, "2", "1", null},
            // Android/WebOS/QNX/Bada/RIM/Maemo/MeeGo/Sailfish OS/OpenHarmony
            {"(android|webos|qnx|bada|rim tablet os|maemo|meego|sailfish|openharmony)[-\\/ ]?([\\w\\.]*)", null,  "1", "2" ,null},
            // Blackberry
            {"(blackberry)\\w*\\/([\\w\\.]+)", null, "1", "2" , null},
            // Tizen/KaiOS
            {"(tizen|kaios)[\\/ ]([\\w\\.]+)", null, "1", "2" , null},
            // Series 40
            {"\\((series40);", null, "1", null, null},
            // BlackBerry 10
            {"\\(bb(10);", "BlackBerry", null, "1", null},
            // Symbian
            {"(?:symbian ?os|symbos|s60(?=;)|series60)[-\\/ ]?([\\w\\.]+)", "Symbian", null, "1", null},
            // Firefox OS
            {"mozilla/[\\d\\.]+ \\((?:mobile|tablet|tv|mobile; [\\w ]+); rv:.+ gecko/([\\w\\.]+)", "Firefox OS", null, "1", null},
            // WebOS
            {"web0s;.+rt(tv)", "webOS", null, "1", null},
            {"\\b(?:hp)?wos(?:browser)?\\/([\\w\\.]+)", "webOS", null, "1", null},
            // watchOS
            {"watch(?: ?os[,\\/]|\\d,\\d\\/)([\\d\\.]+)", "watchOS", null, "1", null},
            // Chromium OS
            {"(cros) [\\w]+(?:\\)| ([\\w\\.]+)\\b)", "Chrome OS", null, "2", null},
            // Panasonic Viera
            {"panasonic;(viera)", null, "1", null, null},
            // Netrange
            {"(netrange)mmh", null, "1", null, null},
            // NetTV
            {"(nettv)\\/(\\d+\\.[\\w\\.]+)", null, "1", "2", null},
            // Nintendo/Playstation
            {"(nintendo|playstation) (\\w+)", null, "1", "2", null},
            // Microsoft Xbox (360, One, X, S, Series X, Series S)
            {"(xbox); +xbox ([^\\);]+)", null, "1", "2", null},
            // Pico
            { "(pico) .+os([\\w\\.]+)", null, "1", "2", null},
            // Joli/Palm
            {"\\b(joli|palm)\\b ?(?:os)?/?([\\w\\.]*)", null, "1", "2", null},
            // Mint
            {"(mint)[\\/\\(\\) ]?(\\w*)", null, "1", "2", null},
            // Mageia/VectorLinux
            {"(mageia|vectorlinux)[; ]", null, "1", null, null},
            // Ubuntu/Debian/SUSE/Gentoo/Arch/Slackware/Fedora/Mandriva/CentOS/PCLinuxOS/RedHat/Zenwalk/Linpus/Raspbian/Plan9/Minix/RISCOS/Contiki/Deepin/Manjaro/elementary/Sabayon/Linspire
            {"([kxln]?ubuntu|debian|suse|opensuse|gentoo|arch(?= linux)|slackware|fedora|mandriva|centos|pclinuxos|red ?hat|zenwalk|linpus|raspbian|plan 9|minix|risc os|contiki|deepin|manjaro|elementary os|sabayon|linspire)(?: gnu\\/linux)?(?: enterprise)?(?:[- ]linux)?(?:-gnu)?[-\\/ ]?(?!chrom|package)([-\\w\\.]*)", null, "1", "2", null},
            // Hurd/Linux
            {"(hurd|linux) ?([\\w\\.]*)", null, "1", "2", null},
            // GNU
            {"(gnu) ?([\\w\\.]+)", null, "1", "2", null},
            // FreeBSD/NetBSD/OpenBSD/PC-BSD/GhostBSD/DragonFly
            {"\\b([-frentopcghs]{0,5}bsd|dragonfly)[/ ]?(?!amd|[ix346]{1,2}86)([\\w\\.]*)", null, "1", "2", null},
            // Haiku
            {"(haiku) (\\w+)", null, "1", "2", null},
            // Solaris
            {"(sunos) ?([\\w\\.\\d]+)", "Solaris", null, "2", null},
            {"((?:open)?solaris)[-\\/ ]?([\\w\\.]+)", null, "1", "2", null},
            // AIX
            {"(aix) ((\\d)(?=\\.|\\)| )[\\w\\.])*", null, "1", "2", null},
            // BeOS/OS2/AmigaOS/MorphOS/OpenVMS/Fuchsia/HP-UX/SerenityOS
            {"\\b(beos|os\\/2|amigaos|morphos|openvms|fuchsia|hp-ux|serenityos)", null, "1", null, null},
            // UNIX
            {"(unix) ?([\\w\\.]*)", null, "1", "2", null},
    };

    public static final String[][] DEVICE_PATTERNS = {
            // !!!! {  regex, default model,  № model, default vendor, № vendor, default type, № type, modifier } !!!

            //////////////////////////
            // MOBILES & TABLETS
            /////////////////////////

            // Samsung
            {"\\b(sch-i[89]0\\d|shw-m380s|sm-[ptx]\\w{2,4}|gt-[pn]\\d{2,4}|sgh-t8[56]9|nexus 10)", null, "1", "Samsung", null, TABLET, null, null},
            {"\\b((?:s[cgp]h|gt|sm)-(?![lr])\\w+|sc[g-]?\\d+a?|galaxy nexus)", null, "1", "Samsung", null, MOBILE, null, null},
            {"samsung[- ]((?!sm-[lr])[-\\w]+)", null, "1", "Samsung", null, MOBILE, null, null},
            {"sec-(sgh\\w+)", null, "1", "Samsung", null, MOBILE, null, null},
            // iPod/iPhone
            {"(?:\\/|\\()(ip(?:hone|od)[\\w, ]*)(?:\\/|;)", null, "1", "Apple", null, MOBILE, null, null},
            // iPad
            {"\\((ipad);[-\\w\\),; ]+apple", null, "1", "Apple", null, TABLET, null, null},
            {"applecoremedia\\/[\\w\\.]+ \\((ipad)", null, "1", "Apple", null, TABLET, null, null},
            {"\\b(ipad)\\d\\d?,\\d\\d?[;\\]].+ios", null, "1", "Apple", null, TABLET, null, null},
            {"(macintosh);", null, "1", "Apple", null, null, null, null},
            // Sharp
            {"\\b(sh-?[altvz]?\\d\\d[a-ekm]?)", null, "1", "Sharp", null, MOBILE, null, null},
            // Honor
            {"\\b((?:brt|eln|hey2?|gdi|jdn)-a?[lnw]09|(?:ag[rm]3?|jdn2|kob2)-a?[lw]0[09]hn)(?: bui|\\)|;)", null, "1", "Honor", null, TABLET, null, null},
            {"honor([\\-\\w ]+)[;\\)]", null, "1", "Honor", null, MOBILE, null, null},
            // Huawei
            {"\\b((?:ag[rs][2356]?k?|bah[234]?|bg[2o]|bt[kv]|cmr|cpn|db[ry]2?|jdn2|got|kob2?k?|mon|pce|scm|sht?|[tw]gr|vrd)-[ad]?[lw][0125][09]b?|605hw|bg2-u03|(?:gem|fdr|m2|ple|t1)-[7a]0[1-4][lu]|t1-a2[13][lw]|mediapad[\\w\\. ]*(?= bui|\\)))\\b(?!.+d/s)", null, "1", "Huawei", null, TABLET, null, null},
            {"(?:huawei)([-\\w ]+)[;\\)]", null, "1", "Huawei", null, MOBILE, null, null},
            {"\\b(nexus 6p|\\w{2,4}e?-[atu]?[ln][\\dx][012359c][adn]?)\\b(?!.+d/s)", null, "1", "Huawei", null, MOBILE, null, null},
            // Xiaomi
            {"oid[^\\)]+; (2[dbc]{4}(182|283|rp\\w{2})[cgl]|m2105k81a?c)(?: bui|\\))", null, "1", "Xiaomi", null, TABLET, null, "replaceAll"},
            {"\\b((?:red)?mi[-_ ]?pad[\\w- ]*)(?: bui|\\))", null, "1", "Xiaomi", null, TABLET, null, "replaceAll"},
            {"\\b(poco[\\w ]+|m2\\d{3}j\\d\\d[a-z]{2})(?: bui|\\))", null, "1", "Xiaomi", null, MOBILE, null, "replaceAll"},
            {"\\b; (\\w+) build\\/hm\\1", null, "1", "Xiaomi", null, MOBILE, null, "replaceAll"},
            {"\\b(hm[-_ ]?note?[_ ]?(?:\\d\\w)?) bui", null, "1", "Xiaomi", null, MOBILE, null, "replaceAll"},
            {"\\b(redmi[\\-_ ]?(?:note|k)?[\\w_ ]+)(?: bui|\\))", null, "1", "Xiaomi", null, MOBILE, null, "replaceAll"},
            {"oid[^\\)]+; (m?[12][0-389][01]\\w{3,6}[c-y])( bui|; wv|\\))", null, "1", "Xiaomi", null, MOBILE, null, "replaceAll"},
            {"\\b(mi[-_ ]?(?:a\\d|one|one[_ ]plus|note lte|max|cc)?[_ ]?(?:\\d?\\w?)[_ ]?(?:plus|se|lite|pro)?)(?: bui|\\))", null, "1", "Xiaomi", null, MOBILE, null, "replaceAll"},
            {" ([\\w ]+) miui\\/v?\\d", null, "1", "Xiaomi", null, MOBILE, null, "replaceAll"},
            // OPPO
            {"; (\\w+) bui.+ oppo", null, "1", "OPPO", null, MOBILE, null, null},
            {"\\b(cph[12]\\d{3}|p(?:af|c[al]|d\\w|e[ar])[mt]\\d0|x9007|a101op)\\b", null, "1", "OPPO", null, MOBILE, null, null},
            {"\\b(opd2(\\d{3}a?))(?: bui|\\))", null, "1", null, "2", TABLET, null, "vendorMapOPPO"},
            // Vivo
            {"vivo (\\w+)(?: bui|\\))", null, "1", "Vivo", null, MOBILE, null, null},
            {"\\b(v[12]\\d{3}\\w?[at])(?: bui|;)", null, "1", "Vivo", null, MOBILE, null, null},
            // Realme
            {"\\b(rmx[1-3]\\d{3})(?: bui|;|\\))", null, "1", "Realme", null, MOBILE, null, null},
            // Motorola
            {"\\b(milestone|droid(?:[2-4x]| (?:bionic|x2|pro|razr))?:?( 4g)?)\\b[\\w ]+build\\/", null, "1", "Motorola", null, MOBILE, null, null},
            {"\\bmot(?:orola)?[- ](\\w*)", null, "1", "Motorola", null, MOBILE, null, null},
            {"((?:moto(?! 360)[\\w\\(\\) ]+|xt\\d{3,4}|nexus 6)(?= bui|\\)))", null, "1", "Motorola", null, MOBILE, null, null},
            {"\\b(mz60\\d|xoom[2 ]{0,2}) build\\/", null, "1", "Motorola", null, TABLET, null, null},
            // LG
            {"((?=lg)?[vl]k\\-?\\d{3}) bui| 3\\.[-\\w; ]{10}lg?-([06cv9]{3,4})", null, "1", "LG", null, TABLET, null, null},
            {"(lm(?:-?f100[nv]?|-[\\w\\.]+)(?= bui|\\))|nexus [45])", null, "1", "LG", null, MOBILE, null, null},
            {"\\blg[-e;\\/ ]+((?!browser|netcast|android tv|watch)\\w+)", null, "1", "LG", null, MOBILE, null, null},
            {"\\blg-?([\\d\\w]+) bui", null, "1", "LG", null, MOBILE, null, null},
            // Lenovo
            {"(ideatab[-\\w ]+|602lv|d-42a|a101lv|a2109a|a3500-hv|s[56]000|pb-6505[my]|tb-?x?\\d{3,4}(?:f[cu]|xu|[av])|yt\\d?-[jx]?\\d+[lfmx])( bui|;|\\)|\\/)", null, "1", "Lenovo", null, TABLET, null, null},
            {"lenovo ?(b[68]0[08]0-?[hf]?|tab(?:[\\w- ]+?)|tb[\\w-]{6,7})( bui|;|\\)|\\/)", null, "1", "Lenovo", null, TABLET, null, null},
            // Nokia
            {"(nokia) (t[12][01])", null, "2", null, "1", TABLET, null, null},
            {"(?:maemo|nokia).*(n900|lumia \\d+)", null, "1", "Nokia", null, MOBILE, null, "replaceAll"},
            {"nokia[-_ ]?(([-\\w\\.]*))", null, "1", "Nokia", null, MOBILE, null, "replaceAll"},
            // Google
            {"(pixel (c|tablet))\\b", null, "1", "Google", null, TABLET, null, null},
            {"droid.+; (pixel[\\daxl ]{0,6})(?: bui|\\))", null, "1", "Google", null, MOBILE, null, null},
            // Sony
            {"(?i)droid.+; (a?\\d[0-2]{2}so|[c-g]\\d{4}|so[-gl]\\w+|xq-a\\w[4-7][12])(?= bui|\\).+chrome\\/(?![1-6]{0,1}\\d\\.))", null, "1", "Sony", null, MOBILE, null, null},
            {"(?i)sony tablet [ps]", "Xperia Tablet", null, "Sony", null, TABLET, null, null},
            {"(?i)\\b(?:sony)?sgp\\w+(?: bui|\\))", "Xperia Tablet", null, "Sony", null, TABLET, null, null},
            // OnePlus
            {"(?i)(kb2005|in20[12]5|be20[12][59])\\b", null, "1", "OnePlus", null, MOBILE, null, null},
            {"(?i)(?:one)?(?:plus)? (a\\d0\\d\\d)(?: b|\\))", null, "1", "OnePlus", null, MOBILE, null, null},
            // Amazon
            {"(?i)(alexa)webm", null, "1", "Amazon", null, TABLET, null, null},
            {"(?i)(kf[a-z]{2}wi|aeo(?!bc)\\w\\w)( bui|\\))", null, "1", "Amazon", null, TABLET, null, null},
            {"(?i)(kf[a-z]+)( bui|\\)).+silk\\/", null, "1", "Amazon", null, TABLET, null, null},
            {"(?i)((?:sd|kf)[0349hijorstuw]+)( bui|\\)).+silk\\/", null, "1", "Amazon", null, MOBILE, null, "Fire Phone "},
            // BlackBerry
            {"(playbook);[-\\w\\),; ]+(rim)", null, "1", null, "2", TABLET, null, null},
            {"\\b((?:bb[a-f]|st[hv])100-\\d)", null, "1", "BlackBerry", null, MOBILE, null, null},
            {"\\(bb10; (\\w+)", null, "1", "BlackBerry", null, MOBILE, null, null},
            // Asus
            {"(?:\\b|asus_)(transfo[prime ]{4,10} \\w+|eeepc|slider \\w+|nexus 7|padfone|p00[cj])", null, "1", "ASUS", null, TABLET, null, null},
            {" (z[bes]6[027][012][km][ls]|zenfone \\d\\w?)\\b", null, "1", "ASUS", null, MOBILE, null, null},
            // HTC
            {"(nexus 9)", null, "1", "HTC", null, TABLET, null, null},
            {"(htc)[-;_ ]{1,2}([\\w ]+(?=\\)| bui)|\\w+)", null, "2", null, "1", MOBILE, null, "replaceAll"},
            // ZTE
            {"(zte)[- ]([\\w ]+?)(?: bui|/|\\))", null, "2", null, "1", MOBILE, null, "replaceAll"},
            // Alcatel/GeeksPhone/Nexian/Panasonic/Sony
            {"(alcatel|geeksphone|nexian|panasonic(?!(?:;|\\.))|sony(?!-bra))[-_ ]?([-\\w]*)", null, "2", null, "1", MOBILE, null, "replaceAll"},
            // TCL
            {"tcl (xess p17aa)", null, "1", "TCL", null, TABLET, null, null},
            {"droid [\\w\\.]+; ((?:8[14]9[16]|9(?:0(?:48|60|8[01])|1(?:3[27]|66)|2(?:6[69]|9[56])|466))[gqswx])(_\\w(\\w|\\w\\w))?(\\)| bui)", null, "1", "TCL", null, TABLET, null, null},
            {"droid [\\w\\.]+; (418(?:7d|8v)|5087z|5102l|61(?:02[dh]|25[adfh]|27[ai]|56[dh]|59k|65[ah])|a509dl|t(?:43(?:0w|1[adepqu])|50(?:6d|7[adju])|6(?:09dl|10k|12b|71[efho]|76[hjk])|7(?:66[ahju]|67[hw]|7[045][bh]|71[hk]|73o|76[ho]|79w|81[hks]?|82h|90[bhsy]|99b)|810[hs]))(_\\w(\\w|\\w\\w))?(\\)| bui)", null, "1", "TCL", null, MOBILE, null, null},
            // itel
            {"(?i)(itel) (\\w+)", null, "2", null, "1", null, null, "typeMapITEL"},
            // Acer
            {"droid.+; ([ab][1-7]-?[0178a]\\d\\d?)", null, "1", "Acer", null, TABLET, null, null},
            // Meizu
            {"droid.+; (m[1-5] note) bui", null, "1", "Meizu", null, MOBILE, null, null},
            {"\\bmz-([\\-\\w]{2,})", null, "1", "Meizu", null, MOBILE, null, null},
            // Ulefone
            {"; ((?:power )?armor(?:[\\w ]{0,8}))(?: bui|\\))", null, "1", "Ulefone", null, MOBILE, null, null},
            // Energizer
            {"; (energy ?\\w+)(?: bui|\\))", null, "1", "Energizer", null, MOBILE, null, null},
            {"; energizer ([\\w ]+)(?: bui|\\))", null, "1", "Energizer", null, MOBILE, null, null},
            // Cat
            {"; cat (b35);", null, "1", "Cat", null, MOBILE, null, null},
            {"; (b15q?|s22 flip|s48c|s62 pro)(?: bui|\\))", null, "1", "Cat", null, MOBILE, null, null},
            // Smartfren
            {"((?:new )?andromax[\\w- ]+)(?: bui|\\))", null, "1", "Smartfren", null, MOBILE, null, null},
            // Nothing
            {"droid.+; (a(?:015|06[35]|142p?))", null, "1", "Nothing", null, MOBILE, null, null},
            // IMO
            {"(imo) (tab \\w+)", null, "2", null, "1", TABLET, null, null},
            // Infinix XPad
            {"(infinix) (x1101b?)", null, "2", null, "1", TABLET, null, null},
            // BlackBerry/BenQ/Palm/Sony-Ericsson/Acer/Asus/Dell/Meizu/Motorola/Polytron/Infinix/Tecno/Micromax/Advan
            {"(blackberry|benq|palm(?=\\-)|sonyericsson|acer|asus(?! zenw)|dell|jolla|meizu|motorola|polytron|infinix|tecno|micromax|advan)[-_ ]?([\\-\\w]*)", null, "2", null, "1", MOBILE, null, null},
            // HMD/IMO
            {"; (hmd|imo) ([\\w ]+?)(?: bui|\\))", null, "2", null, "1", MOBILE, null, null},
            // HP iPAQ
            {"(hp) ([\\w ]+\\w)", null, "2", null, "1", MOBILE, null, null},
            // Microsoft Lumia
            {"(microsoft); (lumia[\\w ]+)", null, "2", null, "1", MOBILE, null, null},
            // Lenovo
            {"(lenovo)[-_ ]?([\\-\\w ]+?)(?: bui|\\)|\\/)", null, "2", null, "1", MOBILE, null, null},
            // OPPO
            {"(oppo) ?([\\w ]+) bui", null, "2", null, "1", MOBILE, null, null},
            // Kobo
            {"(kobo)\\s(ereader|touch)", null, "2", null, "1", TABLET, null, null},
            // Archos
            {"(archos)\\s(gamepad2?)", null, "2", null, "1", TABLET, null, null},
            // HP TouchPad
            {"(hp).+(touchpad(?!.+tablet)|tablet)", null, "2", null, "1", TABLET, null, null},
            // Kindle
            {"(kindle)\\/([\\w\\.]+)", null, "2", null, "1", TABLET, null, null},
            // Surface Duo
            {"(surface duo)", null, "1", "Microsoft", null, TABLET, null, null},
            // Fairphone
            {"droid [\\d\\.]+; (fp\\du?)(?: b|\\))", null, "1", "Fairphone", null, MOBILE, null, null},
            // Nvidia Shield Tablets
            {"(shield[\\w ]+) b", null, "1", "Nvidia", null, TABLET, null, null},
            // Sprint Phones
            {"(sprint) (\\w+)", null, "2", null, "1", MOBILE, null, null},
            // Microsoft Kin
            {"(kin\\.[onetw]{3})", null, "1", "Microsoft", null, MOBILE, null, "replaceAll"},
            // Zebra
            {"droid.+; ([c6]+|et5[16]|mc[239][23]x?|vc8[03]x?)\\)", null, "1", "Zebra", null, TABLET, null, null},
            {"droid.+; (ec30|ps20|tc[2-8]\\d[kx])\\)", null, "1", "Zebra", null, MOBILE, null, null},

            ///////////////////
            // SMARTTVS
            ///////////////////

            // Samsung
            {"smart-tv.+(samsung)", null, null, null, "1", SMARTTV, null, null},
            // HbbTV Samsung
            {"hbbtv.+maple;(\\d+)", null, "1", "Samsung", null, SMARTTV, null, "SmartTV"},
            // LG SmartTV
            {"(nux; netcast.+smarttv|lg (netcast\\.tv-201\\d|android tv))", null, null, "LG", null, SMARTTV, null, null},
            // Apple TV
            {"(apple) ?tv", "Apple TV", null, null, "1", SMARTTV, null, null},
            // Google Chromecast Third Generation
            {"crkey.*devicetype\\/chromecast", "Chromecast Third Generation", null, "Google", null, SMARTTV, null, null},
            // Google Chromecast with specific device type
            {"crkey.*devicetype\\/([^/]*)", null, "1", "Google", null, SMARTTV, null, "Chromecast "},
            // Google Chromecast Nest Hub
            {"fuchsia.*crkey", "Chromecast Nest Hub", null, "Google", null, SMARTTV, null, null},
            // Google Chromecast
            {"crkey", "Chromecast", null, "Google", null, SMARTTV, null, null},
            // Fire TV
            {"droid.+aft(\\w+)( bui|\\))", null, "1", "Amazon", null, SMARTTV, null, null},
            // Sharp
            {"\\(dtv[\\);].+(aquos)", null, "1", "Sharp", null, SMARTTV, null, null},
            {"(aquos-tv[\\w ]+)\\)", null, "1", "Sharp", null, SMARTTV, null, null},
            // Sony
            {"(bravia[\\w ]+)( bui|\\))", null, "1", "Sony", null, SMARTTV, null, null},
            // Xiaomi
            {"(mi(tv|box)-?\\w+) bui", null, "1", "Xiaomi", null, SMARTTV, null, null},
            // TechniSAT
            {"Hbbtv.*(technisat) (.*);", null, "2", null, "1", SMARTTV, null, null},
            // Roku

            {"\\b(roku)[\\dx]*[\\)\\/]*((?:dvp-)?[\\d\\.]*).*", null, "2", null, "1", SMARTTV, null, null},
            // HbbTV devices
            {"hbbtv/\\d+\\.\\d+\\.\\d+ +\\([\\w\\+ ]*; *([\\w\\d][^;]*);([^;]*)", null, "2", null, "1", SMARTTV,  null, "trim"},
            // SmartTV from Unidentified Vendors
            {"\\b(android tv|smart[- ]?tv|opera tv|tv; rv:)\\b", null, null, null, null, SMARTTV, null, null},

            ///////////////////
            // CONSOLES
            ///////////////////

            // Ouya
            {"(ouya)", null, null, null, "1", CONSOLE, null, null},
            // Nintendo
            {"(nintendo) (\\w+)", null, "2", null, "1", CONSOLE, null, null},
            // Nvidia
            {"droid.+; (shield) bui", null, "1", "Nvidia", null, CONSOLE, null, null},
            // Playstation
            {"(playstation \\w+)", null, "1", "Sony", null, CONSOLE, null, null},
            // Microsoft Xbox
            {"\\b(xbox(?: one)?(?!; xbox))[\\); ]", null, "1", "Microsoft", null, CONSOLE, null, null},

            ///////////////////
            // WEARABLES
            ///////////////////

            // Samsung Galaxy Watch
            {"\\b(sm-[lr]\\d\\d[0156][fnuw]?s?|gear live)\\b", null, "1", "Samsung", null, WEARABLE, null, null},
            // Pebble
            {"((pebble))app", null, "2", null, "1", WEARABLE, null, null},
            // Asus ZenWatch / LG Watch / Pixel Watch
            {"(asus|google|lg|oppo) ((pixel |zen)?watch[\\w ]*)( bui|\\))", null, "2", null, "1", WEARABLE, null, null},
            // Oppo Watch
            {"(ow(?:19|20)?we?[1-3]{1,3})", null, "1", "OPPO", null, WEARABLE, null, null},
            // Apple Watch
            {"(watch)(?: ?os[,\\/]|\\d,\\d\\/)[\\d\\.]+", null, "1", "Apple", null, WEARABLE, null, null},
            // OnePlus Watch
            {"(opwwe\\d{3})", null, "1", "OnePlus", null, WEARABLE, null, null},
            // Motorola 360
            {"(moto 360)", null, "1", "Motorola", null, WEARABLE, null, null},
            // Sony SmartWatch
            {"(smartwatch 3)", null, "1", "Sony", null, WEARABLE, null, null},
            // LG G Watch R
            {"(g watch r)", null, "1", "LG", null, WEARABLE, null, null},
            {"droid.+; (wt63?0{2,3})\\)", null, "1", "Zebra", null, WEARABLE, null, null},

            ///////////////////
            // XR
            ///////////////////

            // Google Glass
            {"droid.+; (glass) \\d", null, "1", "Google", null, XR, null, null},
            // Pico
            {"(pico) (4|neo3(?: link|pro)?)", null, "2", null, "1",XR, null, null},
            // Oculus Quest
            {"; (quest( \\d| pro)?)", null, "1", "Facebook", null, XR, null, null},

            ///////////////////
            // EMBEDDED
            ///////////////////

            // Tesla
            {"(tesla)(?: qtcarbrowser|\\/[-\\w\\.]+)", null, null, null, "1", EMBEDDED, null, null},
            // Echo Dot
            {"(aeobc)\\b", null, "1", "Amazon", null, EMBEDDED, null, null},

            ////////////////////
            // MIXED (GENERIC)
            ///////////////////

            // Android Phones from Unidentified Vendors
            {"droid .+?; ([^;]+?)(?: bui|; wv\\)|\\) applew).+? mobile safari", null, "1", null, null, MOBILE, null, null},
            // Android Tablets from Unidentified Vendors
            {"droid .+?; ([^;]+?)(?: bui|\\) applew).+?(?! mobile) safari", null, "1", null, null, TABLET, null, null},
            // Unidentifiable Tablet
            {"\\b((tablet|tab)[;\\/]|focus\\/\\d(?!.+mobile))", null, null, null, null, TABLET, null, null},
            // Unidentifiable Mobile
            {"(phone|mobile(?:[;\\/]| [ \\w\\/\\.]*safari)|pda(?=.+windows ce))", null, null, null, null, MOBILE, null, null},
            // Generic Android Device
            {"(android[-\\w\\. ]{0,9});.+buil", null, "1", "Generic", null, null, null, null},

};
}
