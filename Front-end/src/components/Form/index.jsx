import {Box} from '@mui/material';

import IconButton from '@mui/material/IconButton';
import Visibility from '@mui/icons-material/Visibility';
import Input from '@mui/material/Input';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import FormControl from '@mui/material/FormControl';
import InputLabel from '@mui/material/InputLabel';
import InputAdornment from '@mui/material/InputAdornment';

import Botao from '../Botao'
import { ImgLogo } from './style';
import { useState } from 'react';



function Form() {
    
    const [showPassword, setShowPassword] = useState(false);
    const handleClickShowPassword = () => setShowPassword((show) => !show)
    const handleMouseDownPassword = (event) => {
        event.preventDefault();
    }
    return (
        
       <Box 
            component='div'
            sx={{textAlign:'center',
                display:'flex',
                justifyContent:'center',
                alignItems:'center',
                flexDirection:'column',
                height:'100%',
                '@media (min-width:1500px)':{
                    height: '100vh'
                }
                }}>
                   
            <Box 
                component='form' 
                sx={{
                    '& > :not(style)': { m: 1, width: '32ch' },
                    display:'flex',
                    flexDirection:'column',
                    justifyContent:'center',
                    alignItems:'center',
                    padding:'1rem',
                    border:'1px solid #f3f3f3',
                    gap: '2rem',
                    borderRadius:'10px',
                    background: ' #7c7c7c59',
                    boxShadow: '0 8px 32px 0 #1f26875e',
                    backdropFilter: 'blur( 13.5px )',
                    WebkitBackdropFilter: 'blur( 13.5px )',
                    marginTop:'3rem',
                    marginBottom:'3rem',
                    '@media (min-width:1500px)':{
                        marginTop: '0rem',
                        padding:'3rem'
                    }
                    
                    

                }}
                noValidate
                autoComplete='off'
                >
                <ImgLogo src='images/img-telainicial.png'/>
                <FormControl sx={{ m: 1, width: '35ch',  '& label.Mui-focused': {
                                color:'#6F7E8C',
                              }, }} variant='standart'>
                        <InputLabel sx={{color:'#f3f3f3'}} htmlFor='standart-adornment-password'>Email</InputLabel>
                    <Input
                        id="outlined-password-input"
                        type='email'
                        sx={{
                            color: '#f3f3f3',
                            '&::before': {
                            borderBottom: '2px solid #E0E3E7'},
                            '&:hover:not(.Mui-disabled, .Mui-error):before': {
                                borderBottom: '2px solid #B2BAC2',
                              },
                            '&.Mui-focused:after': {
                                borderBottom: '2px solid #6F7E8C ',
                              }, 
                             
                            
                        }}
                       
                    />
                  
                    </FormControl>
                    
                    <FormControl sx={{ m: 1, width: '35ch',  '& label.Mui-focused': {
                                color:'#6F7E8C',
                              }, }} variant='standart'>
                        <InputLabel sx={{color:'#f3f3f3'}} htmlFor='standart-adornment-password'>Senha</InputLabel>
                    <Input
                        id="outlined-password-input"
                        type={showPassword ? 'text' : 'password'}
                        sx={{
                            color: '#f3f3f3',
                            '&::before': {
                            borderBottom: '2px solid #E0E3E7'},
                            '&:hover:not(.Mui-disabled, .Mui-error):before': {
                                borderBottom: '2px solid #B2BAC2',
                              },
                            '&.Mui-focused:after': {
                                borderBottom: '2px solid #6F7E8C ',
                              }, 
                             
                            
                        }}
                        endAdornment={
                            <InputAdornment>
                                <IconButton
                                    aria-label="toggle password visibility"
                                    onClick={handleClickShowPassword}
                                    onMouseDown={handleMouseDownPassword}
                                    edge='end'
                                    
                                    sx={{color:'#f3f3f3',}}
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