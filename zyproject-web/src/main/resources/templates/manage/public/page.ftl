<#macro fpage page pagesize totalpages totalrecords url>
    <li><span>共条${totalrecords}记录&nbsp;&nbsp;第${page}页/共${totalpages}页</span></li>

    <#--startpage:起始页码就是page，endpage:结束页码，showfirstpage是否显示首页按钮，showlastpage：是否显示末页按钮，showpre是否显示前...，shownext是否显示后...-->
    <#assign startpage = page,endpage=10,showfirstpage=false,showlastpage=false,showpre=false,shownext=false,prepage = 1,nextpage=11>
    <#--是否显示首页按钮及计算初始页码-->
    <#if page gt 1>
        <#assign showfirstpage = true>
        <#--startpage向前挪4页，如果不足4页，则startpage=1-->
        <#assign startpage=(page-4)>
        <#if startpage lte 0>
            <#assign startpage = 1>
        </#if>
    </#if>
    <#--是否显示前n页的...，以及...的链接-->
    <#if page gt 5>
        <#assign showpre = true,prepage=page-5>
    </#if>
    <#--计算endpage-->
    <#if page+pagesize-1 lt totalpages>
        <#assign endpage = page+pagesize-1>
        <#--显示后面的...按钮-->
        <#assign shownext = true>
        <#--后面...的页面码-->
        <#assign nextpage=page+pagesize>
        <#--显示末页-->
        <#assign showlastpage = true>
    <#else>
        <#assign endpage = totalpages>
    </#if>
    <#if endpage lte 0>
        <#assign endpage = 1>
    </#if>

    <#--开始展示-->

    <#--首页-->
    <#if showfirstpage>
        <li><span><a href="${url}&page=1">首页</a></span></li>
    </#if>
    <#--前面的...-->
    <#if showpre>
        <li><span><a href="${url}&page=${prepage}">...</a></span></li>
    </#if>
    <#--显示的页码按钮-->
    <#list startpage..endpage as p>
        <li  <#if p == page>class="active"</#if>><span><a href="${url}&page=${p}">${p}</a></span></li>
    </#list>
    <#--后面的...-->
    <#if shownext>
        <li><span><a href="${url}&page=${nextpage}">...</a></span></li>
    </#if>
    <#--显示尾页-->
    <#if showlastpage>
        <li><span><a href="${url}&page=${totalpages}">末页</a></span></li>
    </#if>
</#macro>