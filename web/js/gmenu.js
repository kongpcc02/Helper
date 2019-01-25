/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



(function(){
    try{
        var g,j,k,l,m=window.gbar={};
        function _tvn(a,c){
            var b=parseInt(a,10);
            return isNaN(b)?c:b
        }
        function _tvf(a,c){
            var b=parseFloat(a);
            return isNaN(b)?c:b
        }
        function _tvb(a,c){
            return a=="true"?true:a=="false"?false:c
        }
        m.bv={
            n:_tvn("2",0),
            r:"",
            m:_tvn("0",1)
        };
        var n=function(){
            return m.bv.m==1
        };
        m.sb=n;
        var o,p,q;
        function r(a){
            var c=window.encodeURIComponent&&(document.forms[0].q||"").value;
            if(c)a.href=a.href.replace(/([?&])q=[^&]*|$/,function(b,d){
                return(d||"&")+"q="+encodeURIComponent(c)
                })
        }
        l=r;
        function s(a,c,b,d,h,i){
            var f=document.getElementById(a);
            if(f){
                var e=f.style;
                e.left=d?"auto":c+"px";
                e.right=d?c+"px":"auto";
                e.top=b+"px";
                e.visibility=p?"hidden":"visible";
                if(h&&i){
                    e.width=h+"px";
                    e.height=i+"px"
                    }else{
                    s(o,c,b,d,f.offsetWidth,f.offsetHeight);
                    p=p?"":a
                    }
                }
        }
var t=[],w=function(a){
    a=a||window.event;
    var c=a.target||a.srcElement;
    a.cancelBubble=true;
    if(o==null){
        a=document.createElement(Array.every||window.createPopup?"iframe":"div");
        a.frameBorder="0";
        o=a.id="gbs";
        a.src="#";
        document.body.appendChild(a);
        document.onclick=u
    }
    var b=c;
    c=0;
    if(b.className!="gb3")
        b=b.parentNode;
    a=b.getAttribute("aria-owns")||"gbi";
    var d=b.offsetWidth,h=b.offsetTop>20?46:24;
    if(document.getElementById("tphdr"))
        h-=3;
    var i=false;
    do c+=b.offsetLeft||0;
    while(b=b.offsetParent);
    b=(document.documentElement.clientWidth||document.body.clientWidth)
    -c-d;
    var f;
    d=document.body;
    var e=document.defaultView;
    if(e&&e.getComputedStyle){
        if(d=e.getComputedStyle(d,""))
            f=d.direction
    }else
        f=d.currentStyle?d.currentStyle.direction:d.style.direction;
    f=f=="rtl";
    if(a=="gbi"){
        for(d=0;e=t[d++];)e();
        v(null,window.navExtra);
        if(f){
            c=b;
            i=true
        }
    }else if(!f){
        c=b;
        i=true
    }
    p!=a&&u();
    s(a,c,h,i)
},u=function(){
    p&&s(p,0,0)
},v=function(a,c){
    var b,d=document.getElementById("gbi"),h=a;
    if(!h)
        h=d.firstChild;
    for(;c&&(b=c.pop());){
        var i=d,f=b,e=h;
        q||(q="gb2");
        i.insertBefore(f,e).className=q
    }
},x=function(){
    return document.getElementById("gb_70")
};
m.qs=l;
m.setContinueCb=k;
m.pc=j;
m.tg=w;
m.close=u;
m.addLink=g;
m.almm=v;
m.si=x;
}catch(e){
    window.gbar&&gbar.logger&&gbar.logger.ml(e);
}
})();