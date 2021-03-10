FROM texlive/texlive:latest
RUN mkdir /out
WORKDIR /cv
COPY ./latex /cv
RUN latexmk -pdf -output-directory=/out

FROM nginx:1.18-alpine
COPY ./web /usr/share/nginx/html
COPY --from=0 /out /usr/share/nginx/html/pdf
