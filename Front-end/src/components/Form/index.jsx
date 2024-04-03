import {Box} from '@mui/material';
import IconButton from '@mui/material/IconButton';
import Visibility from '@mui/icons-material/Visibility';
import Input from '@mui/material/Input';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import FormControl from '@mui/material/FormControl';
import InputLabel from '@mui/material/InputLabel';
import InputAdornment from '@mui/material/InputAdornment';
import FormHelperText from '@mui/material/FormHelperText';

import Botao from '../Botao'
import { ImgLogo } from './style';
import { useState } from 'react';
import { stylesForm } from './style';
// import validator from 'validator';





function Form() {

    const [email, setEmail] = useState('');
    const [emailError, setEmailError] = useState('');
    const [senha, setSenha] = useState('');

    const usuarios = [
        {email: 'felipe@gmail.com', senha: 'varefe25'},
        {email: 'lara@gmail.com', senha: 'larinha123'}
    ]

    const validatePassword = (senha) => {
        
    }

    // aqui é o submit do form, que só acontece se emailerror for false, ou seja, não existir erro no email
    const submitForm = (event) => {
        event.preventDefault();
        if(!emailError){
            alert('Formulário enviado')
            setEmail('')
        }else{
            alert('Preencha todos os campos antes de entrar!')
        }
        
    }
    // aqui ele está pegando o que está sendo escrito no input email e armazenando em email
    const handleEmailChange = (event) => {
        setEmail(event.target.value);
      
    }
    // aqui ele mostra o erro no input se o email for inválido apenas ao clicarmos fora no input, e armazena em emailerror
    const handleEmailBlur = () => {
        if(!isValidEmail(event.target.value)) {
            setEmailError('Insira um email válido.')
        }else {
            setEmailError('');
        }
    }
    // aqui é a validação do email
    const isValidEmail = (email) => {
        return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
    }
    
    const [showPassword, setShowPassword] = useState(false);
    const handleClickShowPassword = () => setShowPassword((show) => !show)
    const handleMouseDownPassword = (event) => {
        event.preventDefault();
    }
    return (
        
       <Box component='div' sx={stylesForm.div}>
                   
            <Box 
                component='form' 
                sx={stylesForm.form}
                autoComplete='off'
                noValidate
                onSubmit={submitForm}
                
                >
                <ImgLogo src='images/img-telainicial.png'/>
                <FormControl sx={stylesForm.inputDiv} variant='standart'>
                    <InputLabel sx={stylesForm.inputLabel} htmlFor='standart-adornment-password'>Email</InputLabel>
                    <Input
                        id="outlined-password-input"
                        type='email'
                        value={email}
                        onChange={handleEmailChange}
                        onBlur={handleEmailBlur}
                        error={!!emailError}
                        sx={stylesForm.inputField}
                       
                    />
                  <FormHelperText sx={stylesForm.mensagemErro}>{emailError}</FormHelperText>
                </FormControl>
                    
                    <FormControl sx={stylesForm.inputDiv} variant='standart'>
                        <InputLabel sx={stylesForm.inputLabel} htmlFor='standart-adornment-password'>Senha</InputLabel>
                    <Input
                        id="outlined-password-input"
                        type={showPassword ? 'text' : 'password'}
                        sx={stylesForm.inputField}
                        endAdornment={
                            <InputAdornment>
                                <IconButton
                                    aria-label="toggle password visibility"
                                    onClick={handleClickShowPassword}
                                    onMouseDown={handleMouseDownPassword}
                                    edge='end'
                                    
                                    sx={stylesForm.inputButtom}
                                    >
                                        {showPassword ? <VisibilityOff fontSize='small' /> : <Visibility fontSize='small' />}
        
                                </IconButton>
                            </InputAdornment>
                        }
                    />
                  
                    </FormControl>
                   
                  
                <Botao type='submit' padding='0.8rem 5rem' nome='Entrar'/>
            </Box>       
                    
       </Box>
       


       
    );
}

export default Form