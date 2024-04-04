const createButton = document.getElementById('create-btn');

// 게시글 등록
if (createButton) {
    createButton.addEventListener('click', () => {
        let selectedCategoryId = Number(document.getElementById('category-box').value);
        if (isNaN(selectedCategoryId) || selectedCategoryId === 0) {
            alert('카테고리를 선택하세요.')
            return;
        }

        fetch(`/api/article`, {
            method: 'POST',
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify({
                boardType: document.getElementById('boardType').value,
                categoryId: selectedCategoryId,
                title: document.getElementById('title').value,
                content : document.getElementById('content').value
            }),
        })
            .catch(e => console.error(e))
            .then(response => {
                console.log(response);
                alert('등록이 완료되었습니다.');
                location.replace("/reports");
        });
    });
}

