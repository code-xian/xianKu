import mock from 'mockjs'

const user={
    'username' : 'admin',
    'password' : '123456',
    'token' : '123123123'
}
const user2={
    'username' : 'admin',
    'password' : '123456',
    'token' : '345345345'
}
mock.mock('/login','get',user)
mock.mock('/login1','get',user2)
