let index = {
    init : function(){
        $("#btn-save").on("click",()=>{ //function(){}, ()=>{} this를 바인딩 하기 위해
            this.save();
        });
         $("#btn-delete").on("click",()=>{ //function(){}, ()=>{} this를 바인딩 하기 위해
            this.deleteById();
        });
        $("#btn-update").on("click",()=>{ //function(){}, ()=>{} this를 바인딩 하기 위해
            this.update();
        });
        $("#btn-reply-save").on("click",()=>{ //function(){}, ()=>{} this를 바인딩 하기 위해
            this.replySave();
        });
       
    },

    save : function(){
        //alert("user의 세이브 함수 호출 됨")
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),

        };
        //console.log(data)
        //$.ajax().done().fail(); // ajax통신을 통해서 3개의 파라미터를 json으로 변경하여 insert요청
        // ajax 호출시 default가 비동기 호출
        $.ajax({
            type:"POST",
            url:"/api/board",
            data: JSON.stringify(data), //http body 데이터
            contentType: "application/json; charset=utf-8", //body 데이터가 어떤 타입?
            dataType:"json" //요청을 서버로 해서 응답이 왔을때 기본적으로 모든 것이 문자열, 생긴게 json이라면 => js오브젝트로 변경.
        }).done(function(resp){
            alert("글쓰기완료되었습니다.");
            console.log(resp);
            location.href="/";
        }).fail(function(){
            alert(JSON.stringify(error));
        });
    },
    deleteById : function(){
     	let id = $("#id").text();
     	
        //$.ajax().done().fail(); // ajax통신을 통해서 3개의 파라미터를 json으로 변경하여 insert요청
        // ajax 호출시 default가 비동기 호출
        $.ajax({
            type:"DELETE",
            url:"/api/board/"+id,
            dataType:"json"
        }).done(function(resp){
            alert("삭제가 완료되었습니다.");
            console.log(resp);
            location.href="/";
        }).fail(function(){
            alert(JSON.stringify(error));
        });
    },
        update : function(){
        //alert("user의 세이브 함수 호출 됨")
        let id = $("#id").val();
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),

        };
        $.ajax({
            type:"PUT",
            url:"/api/board/"+id,
            data: JSON.stringify(data), //http body 데이터
            contentType: "application/json; charset=utf-8", //body 데이터가 어떤 타입?
            dataType:"json" //요청을 서버로 해서 응답이 왔을때 기본적으로 모든 것이 문자열, 생긴게 json이라면 => js오브젝트로 변경.
        }).done(function(resp){
            alert("글 수정이 완료되었습니다.");
            location.href="/";
        }).fail(function(){
            alert(JSON.stringify(error));
        });
    },
    replySave : function(){
        let data = {
        	userId:$("#userId").val(),
        	boardId:$("#boardId").val(),
            content: $("#reply-content").val()
        };
        
        let boardId = $("#boardId").val();
        console.log(data);
        $.ajax({
            type:"POST",
            url:`/api/board/${data.boardId}/reply`,
            data: JSON.stringify(data), //http body 데이터
            contentType: "application/json; charset=utf-8", //body 데이터가 어떤 타입?
            dataType:"json" //요청을 서버로 해서 응답이 왔을때 기본적으로 모든 것이 문자열, 생긴게 json이라면 => js오브젝트로 변경.
        }).done(function(resp){
            alert("댓글 작성이 완료되었습니다.");
            location.href = `/board/${data.boardId}`;
        }).fail(function(){
            alert(JSON.stringify(error));
        });
    },
    
    replyDelete : function(boardId,replyId){
        $.ajax({
            type:"DELETE",
            url:`/api/board/${boardId}/reply/${replyId}`,
            dataType:"json" //요청을 서버로 해서 응답이 왔을때 기본적으로 모든 것이 문자열, 생긴게 json이라면 => js오브젝트로 변경.
        }).done(function(resp){
            alert("댓글 삭제가 완료되었습니다.");
            location.href = `/board/${boardId}`;
        }).fail(function(){
            alert(JSON.stringify(error));
        });
    },

   
}

index.init();
