let index = {
    init : function(){
        $("#btn-save").on("click",()=>{ //function(){}, ()=>{} this를 바인딩 하기 위해
            this.save();
        });
        $("#btn-update").on("click",()=>{ //function(){}, ()=>{} this를 바인딩 하기 위해
            this.update();
        });
       
    },

    save : function(){
        //alert("user의 세이브 함수 호출 됨")
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val(),

        };
        //console.log(data)
        //$.ajax().done().fail(); // ajax통신을 통해서 3개의 파라미터를 json으로 변경하여 insert요청
        // ajax 호출시 default가 비동기 호출
        $.ajax({
            type:"POST",
            url:"/auth/joinProc",
            data: JSON.stringify(data), //http body 데이터
            contentType: "application/json; charset=utf-8", //body 데이터가 어떤 타입?
            dataType:"json" //요청을 서버로 해서 응답이 왔을때 기본적으로 모든 것이 문자열, 생긴게 json이라면 => js오브젝트로 변경.
        }).done(function(resp){
        	if(resp.status ===500){
        		alert("회원가입에 실패하였습니다");
        	}
        	else{
				alert("회원가입이 완료되었습니다");
				location.href="/";   	
        	}
        	
            console.log(resp);
            
        }).fail(function(){
            alert(JSON.stringify(error));
        });
    },
     update : function(){
        //alert("user의 세이브 함수 호출 됨")
        let data = {
            id: $("#id").val(),
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val(),

        };
        $.ajax({
            type:"PUT",
            url:"/user",
            data: JSON.stringify(data), //http body 데이터
            contentType: "application/json; charset=utf-8", //body 데이터가 어떤 타입?
            dataType:"json" //요청을 서버로 해서 응답이 왔을때 기본적으로 모든 것이 문자열, 생긴게 json이라면 => js오브젝트로 변경.
        }).done(function(resp){
            alert("회원수정이 완료되었습니다.");
            console.log(resp);
            location.href="/";
        }).fail(function(){
            alert(JSON.stringify(error));
        });
    },
   
}

index.init();
