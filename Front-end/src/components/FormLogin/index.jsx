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
import { useState } from 'react';
import {useNavigate} from 'react-router-dom'
import { stylesForm } from './style';
import {useForm} from 'react-hook-form'
import {isEmail} from 'validator';
import { ImgLogo, ParagrafoCadastro, LinkCadastro,TituloLogin } from './style';


function Form() {
    const navigate = useNavigate();
    const goToTelaCadastro = () => {
        navigate('/cadastro')
     }
    const [showPassword, setShowPassword] = useState(false);
    const handleClickShowPassword = () => setShowPassword((show) => !show)
    const handleMouseDownPassword = (event) => {
        event.preventDefault();
    }
    //ao criarmos a API REST, os usuarios virão do Banco de Dados
    const usuarios = [
        {id: 1, email: 'felipe@gmail.com', senha: '12345678'},
        {id: 2, email: 'larinha@gmail.com', senha: 'laralinda'}
    ]
    const {
        register,
        handleSubmit,
        reset, 
        formState: { errors },
    } = useForm();

    const onSubmit = (data) => {
        alert(JSON.stringify(data))
        reset();
    }

    
    return (
        
       <Box component='div' sx={stylesForm.div}>
                    <TituloLogin>Login</TituloLogin> 
            <Box 
                component='form' 
                sx={stylesForm.form}
                autoComplete='off'
                noValidate
                onSubmit={handleSubmit(onSubmit)}
                >
                <ImgLogo src='images/img-telainicial.png'/>
                <FormControl sx={stylesForm.inputDiv} variant='standart'>
                    <InputLabel sx={stylesForm.inputLabel} htmlFor='standart-adornment-password'>Email</InputLabel>
                    <Input
                        type='email'
                        {...register('email', {
                            required: true,
                            validate: (value) => isEmail(value) && usuarios.some(user => user.email === value),
                           
                            
                        })}
                        
                        error={errors.email ? true : false}
                        sx={stylesForm.inputField}
                        
                    />
                  {errors?.email?.type === 'required' && <FormHelperText sx={stylesForm.mensagemErro}>O email é obrigatório.</FormHelperText> }
                  {errors?.email?.type === 'validate' && <FormHelperText sx={stylesForm.mensagemErro}>O email está incorreto.</FormHelperText> }
                </FormControl>
                    
                    <FormControl sx={stylesForm.inputDiv} variant='standart'>
                        <InputLabel sx={stylesForm.inputLabel} htmlFor='standart-adornment-password'>Senha</InputLabel>
                    <Input
                        id="outlined-password-input"
                        type={showPassword ? 'text' : 'password'}
                        {...register('password', {
                            required: true, 
                            minLength: 8,
                            validate: (value) => usuarios.some(user => user.senha === value),
                        })}
                        error={errors.password ? true : false}
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
                    {errors?.password?.type === 'required' && <FormHelperText sx={stylesForm.mensagemErro}>Senha é obrigatória.</FormHelperText> }
                    {errors?.password?.type === 'minLength' && <FormHelperText sx={stylesForm.mensagemErro}>Senha precisa ter no mínimo 8 caracteres.</FormHelperText>}
                    {errors?.password?.type === 'validate' &&  <FormHelperText sx={stylesForm.mensagemErro}>O email ou senha estão incorretos.</FormHelperText> }  
                    </FormControl>
                   
                  
                <Botao type='submit'  padding='0.8rem 5rem' nome='Entrar'/>
            </Box>       
            <ParagrafoCadastro>Ainda não tem cadastro? <LinkCadastro onClick={goToTelaCadastro}>Cadastre-se</LinkCadastro></ParagrafoCadastro>  
       </Box>
       


       
    );
}

export default Form