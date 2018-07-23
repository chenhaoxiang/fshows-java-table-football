<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <title>首展桌上足球大赛</title>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <h3 class="text-center text-success">
                首展第一届桌上足球比赛分组系统
            </h3>
            <h3 class="text-center text-error">
                ${message!""}
            </h3>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>
                        小组编号
                    </th>
                    <th>
                        组员
                    </th>
                    <th>
                        组队时间
                    </th>
                    <th>
                        状态
                    </th>
                </tr>
                </thead>
                <tbody>
                <#if userDTOMap??>
                <#list userDTOMap?keys as key>
                <tr>
                    <td>
                        ${userDTOMap[key].id!""}
                    </td>
                    <td>
                        ${userDTOMap[key].name!""}
                    </td>
                    <td>
                        ${userDTOMap[key].time?string('yyyy-dd-MM HH:mm:ss')!""}
                    </td>
                    <td>
                        ${userDTOMap[key].statusEnum.message!""}
                    </td>
                </tr>
                </#list>
                </#if>
                </tbody>
            </table>
            <form class="text-center" action="/toGroup" method="post">
                <fieldset>
                    <legend>随机分组</legend> <label>姓名：</label><input name="name" type="text" />
                    <span class="help-block">分组保证随机。种子选手曹方毅，王永鹏，陈浩翔，焦朝阳将会分在不同的组。</span><button type="submit" class="btn">提交</button>
                </fieldset>
            </form>

            <h3>
                桌上足球规则链接：<a target="_blank" href="/static/html/rule.html">桌上足球规则</a>
            </h3>
            <h3 class="text-center">
                比赛规划：<a target="_blank" href="https://www.processon.com/view/link/5b555fbbe4b025cf49220988">https://www.processon.com/view/link/5b555fbbe4b025cf49220988</a>
            </h3>
        </div>
    </div>
</div>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>