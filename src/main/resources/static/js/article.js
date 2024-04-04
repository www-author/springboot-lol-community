const createButton = document.getElementById('create-btn');

// 게시글 등록
if (createButton) {
    createButton.addEventListener('click', () => {
        let selectedCategoryId = Number(document.getElementById('category-box').value)
        let boardType = document.getElementById('boardType').value;
        let title = document.getElementById('title').value;
        let content = document.getElementById('content').value;

        let message = '';
        if (isNaN(selectedCategoryId) ||  selectedCategoryId === 0) {
            message = '카테고리를 선택하세요.';
        } else if (!title ||  !title.trim()) {
            message = '제목을 입력하세요.';
        } else if (!content ||  !content.trim()) {
            message = '내용을 입력하세요.';
        }

        if(message.length !== 0) {
            alert(message);
            return;
        }

        fetch(`/api/board/article`, {
            method: 'POST',
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify({
                boardType: boardType,
                categoryId: selectedCategoryId,
                title: title,
                content: content
            }),
        })
            .catch(e => console.error(e))
            .then(response => {
                console.log(response);
                alert('등록이 완료되었습니다.');

                location.replace('/board/' + boardType.toLowerCase());
        });
    });
}

