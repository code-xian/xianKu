import mock from 'mockjs'

const user={
    username: 'admit',
    password: '123456',
    token:'123123123'
}

mock.mock('/login','get',user)
