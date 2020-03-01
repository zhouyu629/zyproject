<#macro fpage page pagesize totalpages totalrecords url>
    <li><span>共条${totalrecords}记录&nbsp;&nbsp;第${page}页/共${totalpages}页</span></li>
    <#--如果当前不是第一页，则展示前5页-->
    <#if page gt 1>
        <li><span><a href="${url}&page=1">首页</a></span></li>
        <#--至于是前x页？-->
        <#if page gt 5>
            <#assign prepage = page-5 >
        <#else>
            <#assign prepage=1>
        </#if>
        <#--是否显示...-->
        <#if prepage gt 1>
            <li><span><a href="${url}&page=${page-6}">...</a></span></li>
        </#if>
        <#list prepage ..page-1 as p>
            <li><span><a href="${url}&page=${p}">${p}</a></span></li>
        </#list>
        <#--当前页-->
        <li class="active"><span><a href="${url}&page=${page}">${page}</a></span></li>
        <#--后10-page页-->
        <#if page lt totalpages>
            <#if totalpages lte 10>
                <#list page+1..totalpages as p>
                    <li><span><a href="${url}&page=${p}">${p}</a></span></li>
                </#list>
            <#else>
                <#--如果后面的页数超过5页-->
                <#if totalpages-page gt 5>
                    <#list page+1..page+5 as p>
                        <li><span><a href="${url}&page=${p}">${p}</a></span></li>
                    </#list>
                    <li><span><a href="${url}&page=${page+6}">...</a></span></li>
                <#else>
                    <#list page+1..totalpages as p>
                        <li><span><a href="${url}&page=${p}">${p}</a></span></li>
                    </#list>
                </#if>
            </#if>
            <#--显示尾页-->
            <li><span><a href="${url}&page=${totalpages}">尾页</a></span></li>
        </#if>
    <#else>
        <#--如果总页数大于10页，只显示前十页，后面用...代替-->
        <#if totalpages gt 10>
            <#list 1..10 as p>
                <li <#if p==1>class="active"</#if>><span><a href="${url}&page=${p}">${p}</a></span></li>
            </#list>
            <li><span><a href="${url}&page=${page+10}">...</a></span></li>
        <#else>
            <#list 1..totalpages as p>
                <li <#if p==1>class="active"</#if>><span><a href="${url}&page=${p}">${p}</a></span></li>
            </#list>
        </#if>
        <#--是否显示尾页-->
        <#if totalpages gt 1>
            <li><span><a href="${url}&page=${totalpages}">尾页</a></span></li>
        </#if>
    </#if>
</#macro>