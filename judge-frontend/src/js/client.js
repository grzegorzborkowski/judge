import React from 'react';
import { render } from 'react-dom';
import Cookies from 'universal-cookie';
import axios from 'axios';
import LoginControl from './modules/LoginControl';

const cookies = new Cookies();
axios.defaults.headers.common['Authorization'] = cookies.get("judge.token");

render(
    <LoginControl />,
    document.getElementById('app')
);
