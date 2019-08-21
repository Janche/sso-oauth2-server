// enter快捷键提交表单
const formNode = document.getElementById('login-form')
if (formNode) {
  formNode.addEventListener('keydown', function(event) {
    if (event.target && event.target.nodeName.toUpperCase() === 'INPUT') {
      if (event.keyCode === 13) {
        formNode.submit()
      }
    }
  })
}
