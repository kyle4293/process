const host = 'http://' + window.location.host;
let targetId;
let folderTargetId;

let loginUsername = "";

$(document).ready(function () {
    const auth = getToken();

    if (auth !== undefined && auth !== '') {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', auth);
        });
    } else {
        window.location.href = host + '/api/user/login-page';
        return;
    }

    $.ajax({
        type: 'GET',
        url: `/api/user-info`,
        contentType: 'application/json',
    })
        .done(function (res, status, xhr) {
            const username = res.username;
            loginUsername = res.username;
            const isAdmin = !!res.admin;

            if (!username) {
                window.location.href = '/api/user/login-page';
                return;
            }

            $('#username').text(username);

            showPost();
            showUsers();

        })
        .fail(function (jqXHR, textStatus) {
            logout();
        });

    $.ajax({
        type: "get",
        url: `/api/post/like`,
        contentType: "application/json",
        success: function (response) {
            Array.from(response).forEach(postLike => {
                let heartIcon = document.getElementById(`heart-icon-${postLike.postId}`);
                let heartStatus = heartIcon.src;
                let heartCount = 0;
                let idNumber = heartIcon.id.replace('heart-icon-', '');
                if(parseInt(idNumber) === postLike.postId)
                    heartCount++;
                if (loginUsername === postLike.username && heartStatus.includes('heart.png'))
                    heartIcon.src = "/images/fullHeart.png";

                let heartCountId = document.getElementById(`heart-count-${postLike.postId}`);
                heartCountId.innerText = `좋아요 ${heartCount}개`;
            })
        }
    });

    // id 가 query 인 녀석 위에서 엔터를 누르면 execSearch() 함수를 실행하라는 뜻입니다.
    $('#query').on('keypress', function (e) {
        if (e.key == 'Enter') {
            execSearch();
        }
    });
    $('#close').on('click', function () {
        $('#container').removeClass('active');
    })
    $('#close2').on('click', function () {
        $('#container2').removeClass('active');
    })
    $('.nav div.nav-see').on('click', function () {
        $('div.nav-see').addClass('active');
        $('div.nav-search').removeClass('active');

        $('#see-area').show();
        $('#search-area').hide();
    })
    $('.nav div.nav-search').on('click', function () {
        $('div.nav-see').removeClass('active');
        $('div.nav-search').addClass('active');

        $('#see-area').hide();
        $('#search-area').show();
    })

    $('#see-area').show();
    $('#search-area').hide();
})

function openClose() {
    $('#postingbox').toggle();
}

function makePost() {
    let title = $('#title').val();
    let contents = $('#content').val();

    $.ajax({
        type: "POST",
        url: `/api/post`,
        contentType: "application/json",
        data: JSON.stringify({title: title, contents: contents}),
        success: function (response) {
            alert('게시글이 성공적으로 작성되었습니다.');
            window.location.reload();
        }
    });
}

function deletePost(id) {

    $.ajax({
        type: "delete",
        url: `/api/post/` + id,
        contentType: "application/json",
        success: function (response) {
            alert('게시글이 성공적으로 삭제되었습니다.');
            window.location.reload();
        }
    });
}

function clickHeart(id, writer) {
    let heartIcon = document.getElementById(`heart-icon-${id}`);
    let heartStatus = heartIcon.src;

    if(heartStatus.includes('heart.png')) {
        clickFillHeart(id, writer, heartIcon)
    } else if(heartStatus.includes('fullHeart.png')) {
        clickEmptyHeart(id, heartIcon)
    }
}

function clickFillHeart(id, writer, heartIcon) {
    if (loginUsername === writer) {
        alert('자신의 게시물에는 좋아요를 누를 수 없습니다.');
    } else {
        $.ajax({
            type: "post",
            url: `/api/post/${id}/like`,
            contentType: "application/json",
            success: function (response) {
                heartIcon.src = "/images/fullHeart.png";
            }
        })
    }
}

function clickEmptyHeart(id, heartIcon) {
    heartIcon.src = "/images/heart.png";
    $.ajax({
        type: "delete",
        url: `/api/post/${id}/like`,
        contentType: "application/json",
        success: function (response) {
            heartIcon.src = "/images/heart.png";
        }
    })
}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}


function showPost() {
    $('#posts').empty();
    $.ajax({
        url : `api/post`,
        type : 'get',
        dataType : 'json',
        async : false,
        success : function (response) {
            console.log(response);
            for (let i = 0; i < response.length; i++) {
                    let post = response[i];
                    let tempHtml = addPostItem(post);
                    $('#posts').append(tempHtml);
            }
        },
        error : function (request, status, error) {
            console.log(error)
        }
    })
}

function showUsers() {
    $('#users').empty();
    $.ajax({
        url : `/api/userList`,
        type : 'get',
        dataType : 'json',
        async : false,
        success : function (response) {
            console.log(response);
            for (let i = 0; i < response.length; i++) {
                    let user = response[i];
                    console.log(user.username);
                    let tempHtml = addUserItem(user);
                    $('#users').append(tempHtml);
            }
        },
        error : function (request, status, error) {
            console.log(error);
        }
    })
}

function addUserItem(user) {
    return `<div class="box-recommend-profile">
              <div class="profile-recommend">
                  <img
                          class="img-profile-32px"
                          src="/images/watermelon.jpg"
                          alt="프로필 이미지"
                  />
                  <div>
                      <p class="userName-recommend">${user.username}</p>
                  </div>
              </div>
              <button class="btn-blue" type="button">팔로우</button>
          </div>`;
}

function addPostItem(post) {
    return `<div class="post">
                <div>
                    <article class="box-feed">
                        <div class="head-feed">
                            <div class="profile-feed">
                                <img
                                        class="img-profile-32px"
                                        src="/images/apple.jpg"
                                        alt="프로필 이미지"
                                />
                                <div>
                                    <p class="userName-feed">${post.user_name}</p>
                                    <p class="location-feed">Seoul, Kroea</p>
                                </div>
                            </div>
                            <img class="icon-more" src="/images/more.png" alt="더보기 아이콘"/>
                        </div>
                        <div class="title">
                            <h3>${post.title}</h3>
                        </div>
                        <div class="contents">
                            <p>${post.contents}</p>
                        </div>
                        <div class="icon-feed">
                            <div>
                                <img id="heart-icon-${post.id}" onclick="clickHeart(${post.id}, '${post.user_name}')" class="img-icon" src="/images/heart.png" alt="하트 아이콘"/>
                                <img class="img-icon" src="/images/chat.png" alt="댓글 아이콘"/>
                                <img onclick="deletePost(${post.id})" class="img-icon" src="/images/delete.png" alt="삭제 아이콘"/>
                            </div>
                        </div>

                        <p id="heart-count-${post.id}" class="text-like">좋아요 0개</p>
                    </article>
                </div>
            </div>`;
}


function logout() {
    // 토큰 삭제
    Cookies.remove('Authorization', {path: '/'});
    window.location.href = host + '/api/user/login-page';
}

function getToken() {

    let auth = Cookies.get('Authorization');

    if (auth === undefined) {
        return '';
    }

    // kakao 로그인 사용한 경우 Bearer 추가
    if (auth.indexOf('Bearer') === -1 && auth !== '') {
        auth = 'Bearer ' + auth;
    }

    return auth;
}