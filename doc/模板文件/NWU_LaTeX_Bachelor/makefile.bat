taskkill /f /im AcroRd32.exe
latex main
bibtex main
require\replace_fs main.bbl ]//. ]//
latex main
gbk2uni main
latex main
dvipdfmx main
main.pdf