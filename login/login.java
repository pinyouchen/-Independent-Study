public void Login(View view) {

        if (!validatePassword() | !validateEmail()) {
            return;
        } else {


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait..");

            progressDialog.show();

            str_email = email.getEditText().getText().toString().trim();
            str_password = password.getEditText().getText().toString().trim();


            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    if (response.equalsIgnoreCase("logged in successfully")) {

                        email.getEditText().setText("");
                        password.getEditText().setText("");
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", str_email);
                    params.put("password", str_password);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(request);


        }
    }
