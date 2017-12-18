/**
 * Created by lp on 2017/12/11.
 */
$(function () {
    $('#rate').radialIndicator();
    var val=$("#rate").attr("data-val");
    var radialObj=$("#rate").data('radialIndicator');
    radialObj.option("barColor","orange");
    radialObj.option("percentage",true);
    radialObj.option("barWidth",10);
    radialObj.option("radius",40);
    radialObj.value(val);


    $('#tabs div').click(function () {
        $(this).addClass('tab_active');
        var show=$('#contents .tab_content').eq($(this).index());
        show.show();
        $('#tabs div').not($(this)).removeClass('tab_active');
        $('#contents .tab_content').not(show).hide();
        if($(this).index()==2){
            /**
             * 获取项目投资记录
             *   ajax 拼接tr
             *    追加tr 到 recordList
             */


            // alert("投资用户列表");
            loadInvestRecodesList($("#itemId").val());
        }
    });
    $('#pages li').click(function () {
        $(this).addClass('active');
        console.log($('#contents .tab_content').eq($(this).index()));
       var show = $('#contents .tab_content').eq($(this).index()+2);
         show.show();
        $('#pages li').not($(this)).removeClass('active');
        $('#contents .tab_content').not(show).hide();
    })

});



function toRecharge() {
    $.ajax({
        type:"post",
        url:ctx+"/user/userAuthCheck",
        dataType:"json",
        success:function(data){
            if(data.code==200){
                window.location.href=ctx+"/account/rechargePage";
            }else{
                layer.confirm(data.msg, {
                    btn: ['执行认证','稍后认证'] //按钮
                }, function(){
                        window.location.href=ctx+"/user/auth";
                });
            }
        }
    })
}

function loadInvestRecodesList() {
    var params={};
    $.ajax({
        type:"post",
        url:"http://localhost:9091/busItemInvest/queryBusItemInvestsByItemId?itemId=4003",
        data:params,
        dataType:"json",
        success:function(data) {

            var tablehtml ='';
            var len=data.list.length;
            if(len<=10) {
                for (i = 0; i <= len - 1; i++) {
                    tablehtml += '<tr><td>' + data.list[i].userId + '</td>' +
                        '<td>' + data.list[i].investAmount + '</td>' +
                        '<td>' + new Date(data.list[i].addtime).format("yyyy-MM-dd hh:mm:ss") + '</td></tr>';
                }
                tablehtml += '</table>';
                $("#recordList1").append(tablehtml);
            }else if(len>10&&len<=20){
                for (i=10;i<=len-1;i++){
                    tablehtml += '<tr><td>' + data.list[i].userId + '</td>' +
                        '<td>' + data.list[i].investAmount + '</td>' +
                        '<td>' + new Date(data.list[i].addtime).format("yyyy-MM-dd hh:mm:ss") + '</td></tr>';
                }
                tablehtml += '</table>';
                $("#recordList2").append(tablehtml);

            }else {
                for (i=20;i<=len-1;i++){
                    tablehtml += '<tr><td>' + data.list[i].userId + '</td>' +
                        '<td>' + data.list[i].investAmount + '</td>' +
                        '<td>' + new Date(data.list[i].addtime).format("yyyy-MM-dd hh:mm:ss") + '</td></tr>';
                }
                tablehtml += '</table>';
                $("#recordList3").append(tablehtml);
            }

        }

    })
}